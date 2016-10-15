Probably, it will be helpful to you, as it was to me.

###Short Spring Reference

**Scopes**

1. `singleton` (only one instance is created for a single Spring container [beans are stored in a cache], is used for stateless beans)
2. `prototype` (a new instance is created every time a request is made, is used for stateful beans [destruction callbacks are not called])
3. `request` (a new instance is created for each HTTP request [`@RequestScope`])
4. `session` (a new instance is created once for the lifetime of a HTTP `Session` [`@SessionScope`])
5. `globalSession` (like the `session` for portlet-based web applications [there are no conflicts with servlets])
6. `application` (only one instance is created per `ServletContext` [`@ApplicationScope`])
7. custom scopes (implement the `Scope` interface (four methods to get objects from the scope, remove them from the scope, and allow them to be destroyed) and register it by `ApplicationContext` -> `ConfigurableBeanFactory` -> `registerScope(name, instance)`)

**Bean lifecycle**

1. Initialization

    - the `InitializingBean` class with the `afterPropertiesSet` method [couples to Spring]
    - the `@Bean` annotation with the `initMethod` attribute [Java configuration]
    - the `@PostConstruct` annotation on a method with any given name [the most appropriate way]
    - XML's `init-method` inside the `bean` tag [faugh]

2. Destruction

    - the `DisposableBean` class with the `destroyMethod` method [couples to Spring]
    - the `@Bean` annotation with the `destroyMethod` attribute [Java configuration]
    - the `@PreDestroy` annotation on a method with any given name [the most appropriate way]
    - XML's `destroy-method` inside the `bean` tag [faugh]
    
3. Other callbacks

    * implement the `Lifecycle` to be subscribed to `start`/`stop` context's events
    * implement the `SmartLifecycle` to complement the `Lifecycle` and add 
        * asynchronous closure (a `Runnable` call after all destruction stuff to perform `CountDownLatch#countDown`)
        * priority (`MIN_VALUE` to get started the first and die the last),
        * auto-startup capabilities (startup on refresh events)
        
4. Aware interfaces

    * `ApplicationContextAware` to get a context that has created the bean and is still managing it
    * `BeanNameAware` to get a name from the bean's `BeanDefinition` before an initialization method is call
    * other 

*It's possible to combine lifecycle mechanisms (if each mechanism is configured with a different method name). The order is `@PostConstruct`/`@Predestroy` annotations, callback interfaces, custom methods.  

**Bean definition inheritance**

* use the `ChildBeanDefinition` (since 2.5 `GenericBeanDefinition`) to extend a `BeanDefinition` instance programmatically
* use the XML's `parent` attribute to inherit a bean definition declaratively [`override` to get a parent's property value]
* all settings are inherited except for *depends on*, *autowire mode*, *dependency check*, *singleton*, *lazy init*
* define an `abstract` bean if it is used only as a template for bean creation [to avoid pre-instantiating such beans (the container's `preInstantiateSingletons` will ingore them)] 

**Integration interfaces**

1. `BeanPostProcessor`

    * is scoped per-container
    * provides callback methods (before [`postProcessBeforeInitialization`] and after [`postProcessAfterInitialization`] initialization callbacks) to modify a bean
    * register by
    
        * implementing the `BeanPostProcessor` with the `@Component` annotation
        * using `ConfigurableBeanFactory#addBeanPostProcessor` for conditional logic/coping BBPs for different contexts [`Ordered` doesn't matter in this case, it works only for autodetected BBPs, beans registered in such way are always processed before other]

    * is not eligible for auto-proxying

2. `BeanFactoryPostProcessor`

    * is scoped per-container
    * operates on the bean configuration metadata
    * modifies metadata before the container creates any beans (excluding BPPs)
    * lazy initialization settings will be ignored (as with BBPs)
    * provides a callback method `postProcessBeanFactory(ConfigurableListableBeanFactory)`
    * use the `PropertyPlaceholderConfigurer` BFPP to externalize values from a bean definition in a separate file by `Properties` format
    * the `PropertyOverrideConfigurer` BFPP is like a latter, but can have default values or no values at all for bean properties
    
3. `FactoryBean`

    * uses to provide custom complex initialization logic
    * takes responsibility for instantiating and managing beans by itself
    * consider the `SmartFactoryBean` to get more fine-grained control
    * preface `&` with a bean name to get a `BeanFactory` instance that produces these beans [`getBean("&bean")`]
    
**Annotation-based configuration**

* [-] decentralizes the configuration
* [-] leads to recompiling source code
* [-] calls before XML configuration, the latter can override properties
* [+] makes shorter and more concise configuration

___

1. `@Required`

    * is being processed by a `RequiredAnnotationBeanPostProcessor`
    * enforces required JavaBean properties to have been configured, if a property hasn't been set, throws a `BeanCreationException`
    * doesn't check a value (e.g. `null` checking)
    * a target is a method only

2. `@Autowired` (or `Injected` by JSR 330)

    * is being processed by a `AutowiredAnnotationBeanPostProcessor`
    * a marker for DI [constructors, setters, fields, configuration methods or their mix]
    * is used over arrays/lists/sets to provide all beans of a particular type [use `@Order` (`@Priority`) annotations or implement the `Ordered` interface to put them in a specified order]
    * is used over maps with `String` keys to provide `{{"bean1", bean1}, ... }`
    * is required by default, can set the `required` property to `false` [no exceptions are thrown in contrast with the `@Required`]
    * can't be applied within custom `BFPP`s and `BPP`s (it's possible via XML or `@Bean` methods) *
    
3. `@Primary`

    * gives a preference to a bean over other multiple candidates
    * if exactly one 'primary' bean exists among the candidates, it will be the autowired value
    * is equivalent to the `primary` attribute in XML
    * `@Target({TYPE, METHOD})`

4. `@Qualifier`

    * gives more control than the `@Primary`
    * declares a specific value to autowire [a bean name is considered a default qualifier value]
    * marks other annotations to use them as qualifiers
    * can be applied to typed collection [all matching beans according to the declared qualifiers are injected]
    * `@Target({FIELD, METHOD, PARAMETER, TYPE, ANNOTATION_TYPE})`
    * can be defined in XML like `<qualifier type="className" value="..." .../>`
    * supports generics types (e.g. `StringStore` instances (extend `Store<String>`) will be injected to `Store<String>`) [works with collections/arrays]
    
5. `@Resource`

    * provides injection (JSR 250) by a bean name [falls back to the `@Autowired` and by-type autowiring]
    * works on fields and setters
    * takes a name attribute (a bean name if no value is specified)
    
6. `@PostConstruct`, `@PreDestroy`

    * are processed by a `CommonAnnotationBeanPostProcessor`, read "Bean lifecycle" above
    
*the same relates to the `@Resource`, `@Value` annotations  

**Classpath scanning**

* stereotype annotations`@Component` and `@Controller`, `@Service`, `@Repository` (all mata-annotated with `@Component`)
    
    * are more properly suited for processing by tools or associating with aspects
    * may carry additional semantics in future releases
    
*  meta-annotations

    * is an annotation that can be applied to another annotation (e.g. `@Component` to `@Service`)
    * can also be combined to create composed annotations (e.g. `@RestController` = `@Controller` + `@ResponseBody`)
    * can redeclare attributes from a parent meta-annotation
    
* autodetection support

    * need to add the `@ComponentScan` in a `@Configuration` class with the `basePackages`/`value` attribute
    * [XML] need to add `<context:component-scan>` (in such case, `<context:annotation-config>` can be removed)
    
* scanning filters

    * filter types
        
        * annotation (components are annotated with this *annotation*) [default]
        * assignable (components are assignable to this *class*)
        * aspectj (an AspectJ type expression)
        * regex (a regex expression)
        * custom (a custom `TypeFilter` implementation)
        
    * use the `includeFilters` or `excludeFilters` attributes with the `@ComponentScan`
    * [XML] use the `include-filter` or `exclude-filter` sub-elements of the `component-scan` element
    * use `useDefaultFilters=false` (`use-default-filters="false"`) to disable the default filters (which detect `@Component`, ... , `@Configuration`)
   
+static `@Bean` methods // todo
++the `@Qualifier`, `@Scope` support and annotate type-level targets
+++use a `BeanNameGenerator` implementation to provide a custom bean-naming strategy

**JSR 330 annotations**

* `@Inject` [`@Autowired`] - `@Inject` has no 'required' attribute; can be used with Java 8’s `Optional`
* `@Named`/`@ManagedBean` [`@Component`] - requires you to specify the name explicitly
* `@Singleton` [`@Scope("singleton")`]
* `@Qualifier`/ `@Named` [`@Qualifier`] - `@Qualifier` is just a meta-annotation for building custom qualifiers; concrete qualifiers can be associated through `@Named`
* `Provider` [`ObjectFactory`] - a shorter `get()` method name

+JSR doesn't have equivalents for Spring's `@Value`, `@Required`, `@Lazy`

**Java-based configuration**

1. `@Bean`

    * an indicator that a method instantiates, configures and initializes a new object to be managed by the container
    * can be used with any `@Component`*, but usually with the `@Configuration`
    * `@Target({METHOD, ANNOTATION_TYPE})`

2. `@Configuration`

    * a source of bean definitions
    * `@Target(TYPE)`

3. `@Import`

    *
    
+the `lite` mode - the situation when a `@Bean` method is declared within a `@Component` [one `@Bean` method should not invoke another `@Bean` method]  


### Resources

* no adequate standard classes for access to low-level resources
* no standardized `URL` access to resources from the classpath / a `ServletContext`  
* complications with creating a custom handler

___

1. `InputStreamSource`

    * `InputStream getInputStream()` return a fresh `InputStream` for reading from. You're responsible for its closing. 

2. `Resource`

    * `boolean exists()` - does the resource actually exist in physical form?
    * `boolean isOpen()` - is resource's stream open? `true` - read only once, then close; `false` - for usual resource implementations
    * usually takes a `String` to create a `Resource`
    * a wrapper (when it's possible, e.g. `URLResource` wraps `URL`)
    
3. `Resource` implementations
    
    * `URLResource` [`URL`] - to access any object via a URL (`http:`, `ftp:`, `file:`)
    * `ClassPathResource` - to access to resources from the `classpath:` (provides a `File` if a resource resides in the classpath, otherwise a `URL` [e.g. for`.jar`s])
    * `FileSytemResource` supports as a `File` and as a `URL`
    * `ServletContextResource` interprets relative paths inside web application's root directory, supports stream access, `URL` and `File` (if the resource is physically on the filesystem [archive is expanded])
    * `InputStreamResource` - shouldn't be used if a specific `Resource` implementation exists
    * `ByteArrayResource` - 
    
4. `ResourceLoader`

    * `Resource getResource(String location)`
        
         * returns a corresponding resource depends on the underlying context [!]
         * can force a concrete resource to be used by specifying a prefix

5. `ResourceLoaderAware`

    * `setResourceLoader(ResourceLoader resourceLoader)` to get a `ResourceLoader`, the context supplies itself to provide a loader (all `ApplicationContext`s are `ResourceLoader`s)
    * the same possible with `ApplicationContextAware` but then the whole `ApplicationContext` instance will be provided
    * the same possible with autowiring (Spring 2.5+)

+resources can be dependencies [a type will be chosen by the context, possible to force that]
++supports wildcards in paths (with some restrictions and implications on portability)
+++`/path/` and `path/` are considered equivalent relative paths [if an absolute path is needed, use `UrlResource` with `file:///path`]


### Validation and data binding

1. `Validator`

    * use to validate objects
    * reports about all validation failures to a `Errors` instance
    * has 2 methods
        
        * `supports(Class)` - does a `Validator` supports instances of that type?
        * `validate(Object, Errors)` - validates the given instance, registers errors with the given `Errors` object

2. `ValidationUtils`

    * provides convenience static methods
        
        * for validator invocation like `invokeValidator`
        * for rejecting empty fields and/or whitespaces in values like `rejectIfEmptyOrWhitespace`
        
3. `MessageCodesResolver`

    * when we form an `Errors` instance (e.g. by the `rejectValue`), the underlying implementation won't only register the code
    * additionally it adds a number of error codes
    * consider the default strategy `DefaultMessageCodesResolver`
    
4. `BeanWrapper`

    * offers functionality
    
        * to set and get property values (individually or in bulk), get property descriptors
        * offers functionality to set and get property values (individually or in bulk)
   
    * supports the ability to add `PropertyChangeListener`s and `VetoableChangeListener`s
    * offers support for nested properties to an unlimited depth
        
        * `name` [`getName()`, `setName(String)`]
        * `account.name` [`getAccount().getName()`, `getAccount().setName(String)`]
        
    * provides support for the setting of indexed properties
    
        * `account[2]` [the third element of the indexed property account (list, array, other ordered collection)]
        * `account[key]` [the value by the `k` key of the `account` map]
    
    * usually is used by the `DataBinder` and the `BeanFactory`
  
5. `PropertyEditor`
    
    * the main Spring's concept to make the conversion between an `Object` and a `String` (e.g. the `ClassEditor` is used when you're setting a `Class` property in XML, [other built-in `PropertyEditor` implementations](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-beans-conversion))
    * can be autodetected if it lays in the same place as a bean does and has a name in the format `BeanClass` + `Editor`
    * can be registered via the `BeanInfo` class (`getPropertyDescriptors` [descriptors contain an array of editors]) which lays in the same package
    * other ways to register a custom editor
    
        * `ConfigurableBeanFactory#registerCustomEditor()` [is not recommended]
        * using a special bean factory post-processor `CustomEditorConfigurer` with a `ApplicationContext` [more convenient]
        * creating a custom `PropertyEditorRegistrar` (if you need to use the same set of property editors in several different situations) 
        
*Best validation practices*

* encapsulate the validation logic for each nested class of object in its own `Validator` implementation
* inject validators for a `rich` class
* validate all `Validator` instances in a constructor

**Spring Type Conversion**

1. `Converter`

    * an SPI to the conversion between `S` and `T` (`T convert(S source)`)
    * `S` is guaranteed to be not `null` (throw any unchecked exception if conversion fails) 
    * make your implementation thread-safe

2. `ConverterFactory`

    * centralizes the conversion logic for an entire class hierarchy (`<T extends R> Converter<S, T> getConverter(Class<T> targetType)`)
    
3. `GenericConverter`

    * provides more flexibility but is less strongly typed (`Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType)`)
    * has 2 methods
        
        * `Set<ConvertiblePair> getConvertibleTypes()` - a set of pairs `source` -> `target`  
        * `Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType)` to support converting between multiple source and target types
        
4. `ConditionalGenericConverter`        

    * is a `GenericConverter` with a condition `boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType)`
    * is used when you want to execute a `Converter` if a condition is true
    
5. `ConversionService`
 
    * for executing type conversations at runtime
    * delegates conversion logic to registered converters carrying out type safety [implementation]
    * implements the `ConverterRegistry` to register converters [implementation]
    * is a stateless thread-safe instance
    * mush be instantiated at the container's startup
    * can be injected and used programmatically