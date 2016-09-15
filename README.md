JCA Adapter for opening a socket connection and maintaining the pool.
This has been tested with glassfish4.0

mvn install or mvn package would create a rar file in the target folder

Steps to deploy on glassfish:

1: Start the server and open the admin console
2: Deploy the rar file . Applications > Deploy
3: Create a connection pool. Resources > Connector > Connector Connection Pool > New (You can select the ResourceAdapter which you deployed earlier)
4: Set the minimum and maximum pool size and as of now Transaction support as No transaction or keep it blank

(There is some connection leak issue. Once the maximum limit was reached, Glassfish started throwing exceptions saying
Caused by: com.sun.appserv.connectors.internal.api.PoolingException: In-use connections equal max-pool-size and expired max-wait-time. 
Cannot allocate more connections.). To handle this follow below mentioned instructions.
Important: 
1: Go to the advanced tab and set the leak timeout something > 0 (for example 5). 
2: Check the Leak reclaim checkbox
3: Max Connection Usage to some positive integer

Now create a Connector Resource. Resources > Connectors > Connector Resources > new
Provide some jndi name, such as 	
jndi/yuktixconnection

Select the connection pool created above. (It would appear in the drop down) And you are done.





