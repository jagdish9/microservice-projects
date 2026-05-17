Microservice projects contains examples of:  
*  Idempotency    
    *  Table based  
    *  Redis based  
*  Caching  
    *  Ehcache  
    *  Redis  
*  Rate Limiting  
    *  Redis  
*  Cascading failure protection   
    *  Resilience4j   
*  Distributed Locking  
    *  Redis  
*  Load Balancer  
    *  Nginx  
*  Routing  
    *  Using Api Gateway  
*  Fetching secret from vault  
    *  HashiCorp Vault  
*  Distributed tracing  
    *  Zipkin  
*  Authentication and Authorization  
    *  OAuth2 and JWT (Keycloak) 
*  Service Discovery  
    *  Eureka  
*  Service Monitoring  
    *  Prometheus and Grafana  
*  Synchronous and Asynchronous communication  
    * RestTemplate, WebClient and Kafka
*  Workload Distribution
    * Load balancing
    * Horizantal Scaling (Multiple instances in different VMs)
    * Asynchronous messaging using Kafka
    * Partitioning (Same database) / Sharding (Different database servers)
*  Prevent DeadLock
    * Optimistic and Pessimistic Locking
*  Distributed Transaction
    * Saga Design pattern (Orchestration and Choreography)
*  Client side Load Balancer
    * Spring cloud load balancer
*  External Api Call
    * RestTemplate and WebClient
*  Scheduler run on only one instance
   *  Redis
   *  net.javacrumbs.shedlock
   *  Using yml property (This required code changes, not recommended)
*  Scheduler run on specific VMs
   *  Using yml property (VM starts with specific name like PROD-*)
*  Versioning
   * URI versioning
*  RESTful APIs in UI
   * Swagger
* Filtering and Searching
   * JPA Specification
* Separate READ operations from WRITE operations
   * Use CQRS (Command Query Responsibility Segregation) pattern
