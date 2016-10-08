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

1. BeanPostProcessor

    * is scoped per-container
    * provides callback methods (before [`postProcessBeforeInitialization`] and after [`postProcessAfterInitialization`] initialization callbacks) to modify a bean
    * register by
    
        * implementing the `BeanPostProcessor` with the `@Component` annotation
        * using `ConfigurableBeanFactory#addBeanPostProcessor` for conditional logic/coping BBPs for different contexts [`Ordered` doesn't matter in this case, it works only for autodetected BBPs, beans registered in such way are always processed before other]

    * is not eligible for auto-proxying

2. BeanFactoryPostProcessor

    * is scoped per-container
    * operates on the bean configuration metadata
    * modifies metadata before the container creates any beans (excluding BPPs)
    * lazy initialization settings will be ignored (as with BBPs)
    * provides a callback method `postProcessBeanFactory(ConfigurableListableBeanFactory)`
    * use the `PropertyPlaceholderConfigurer` BFPP to externalize values from a bean definition in a separate file by `Properties` format
    * the `PropertyOverrideConfigurer` BFPP is like a latter, but can have default values or no values at all for bean properties
    
3. FactoryBean

    * uses to provide custom complex initialization logic
    * takes responsibility for instantiating and managing beans by itself
    * consider the `SmartFactoryBean` to get more fine-grained control
    * preface `&` with a bean name to get a `BeanFactory` instance that produces these beans [`getBean("&bean")`]
    
**Annotation-based configuration**
