### AOP with Spring

## Introduction

**Main concepts**

- *aspect* is a concern that cuts across multiple classes (e.g. transaction management);
- *join point* is a point during the execution of a program (e.g method execution, exception handling); 
- *advice* (*interceptor*) is an action at a particular join point that an aspect takes;
- *pointcut* is an expression that matches join points;
- *introduction* is declaring additional methods or fields on an advised object;
- *target* is object being advised by aspects;
- *proxy* is an object created to implement the aspect contracts;
- *wearing* is linking aspects with other application types or objects to create an advised object.

**Capabilities and goals**

- pure Java implementation;
- supports only method execution;
- uses standard default JDK dynamic proxies to proxy interfaces;
- can use CGLIB proxies to proxy classes.

**Enabling @AspectJ Support**

- the `@EnableAspectJAutoProxy` annotation with Java `@Configuration`
- the `<aop:aspectj-autoproxy/>` element with XML based configuration

**Declaring an aspect**

- a regular bean definition [XML]
- a class with the `@Aspect` annotation [Java]

**Declaring a pointcut**

- the `@Pointcut` annotation to define a pointcut
- has two part (pointcut signature [method signature], pointcut expression [a value of `@Pointcut`])
- a pointcut signature must have a `void` return type

**Combining pointcut expressions**

- can be combined using `&&`, `||` and `!`;
- considered a good practice to build more complex pointcut expressions out of smaller ones.