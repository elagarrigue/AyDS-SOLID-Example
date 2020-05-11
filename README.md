# AyDS-SOLID-Example

Ejemplos SOLID [AyDS](https://cs.uns.edu.ar/~ece/ads/)

## v0

Ejemplo de implementación del patrón observer para emisión de eventos de error.

## v1 Single Responsibility

Refactor: `Subject.notify`

## v2 Open Close

Refactor: `Subject.getEventError` -> `EventErrorFactory`


## v3 Liskov

Refactor: `EventError.UnknownError` 


## v4 Interface Segregation

Refactor: `class Subject` -> `class Subject : Observable<EventError>, Publisher<Exception>`


## v5 Dependency Inversion

Refactor: `interface CurrentTimeWrapper` 
