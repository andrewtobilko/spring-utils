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