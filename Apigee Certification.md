# Apigee Certification

1) Which JavaScript statement can be used to raise a fault from a JavaScript policy named
'Weather"?

- A. return-1
- B. return false
- C. context.setVariable("Weather Fault", "true")
- D. throw "Bad Data";



2) As an API Engineer you have been asked to automate the build process for Apigee
deployments. You decide to build a new tool to deploy the API Bundles using the Apigee
Management API. What would be the correct approach?

- A. You should deploy an Apigee microgateway
- B. Management APIs need a separate API product for security reasons.
- C. Each management API you plan to use should be turned into an Apigee Proxy.
- D. Management APIs should be called directly from the tool to the Apigee gateway



3) You have created an OpenAPl specification and begun a sample implementation of the API
Proxy in Apigee Edge Another team is asking for early access for interactive documentation What
should you do1?

- A. Publish using SwaggerUI
- B. Generate web documents using SmartDocs
- C. Send the requesting team the OpenAPl spec.
- D. Create a sample web app that uses your API, and publish the source code.



4) When populating the Quota configuration for an API product, which statement is true?

- A. The Quota specified will automatically be enforced for any Developer App Keys assigned to the API product.
- B. After validating an API key or access token, flow variables are automatically populated with the Quota configuration for later use in a Quota policy
- C. Rate limiting will be enforced precise to the seconds level, even if you configure a per-minute or higher interval
- D. The Quota configuration specified on the API product enforces a global rate limit across all API proxies



5) Which Apigee product feature can be used to host and version your API documentation'?

- A. Edge
- B. API BaaS
- C. Insights
- D. Apigee-127
- E. SmartDocs



6) Your project has just started, and you are observing that bugs are being caught in QA on
features that were previously working You want to provide a self-governing mechanism for the team to identify regression bugs What should you do?

- A. Use static code analysis tools.
- B. Use Behavior Driven Development tools.
- C. Implement automated integration testing on check in.
- D. Ask developers to run unit tests prior to each check in



7) Which HTTP method would NOT be used for CRUD operations?

- A. GET
- B. POST
- C. OPTIONS
- D. PUT



8) The performance testing team would like to improve the transactions per second for an API
you recently developed. The API retrieves partner names and contact information. What should you
do?

- A. Add Caching Policies
- B. Add a Spike Arrest Policy
- C. Add an Assign Message Policy
- D. Add a Statistics Collector Policy



9) You need to log all error responses in your Apigee Edge proxy What should you do?

- A. Use a JavaScript callout
- B. Use a MessageLog policy in (he DefaultFaulIRule.
- C. Use a conditional MessageLog policy in the PostClientFlow
- D. Use a conditional MessageLog policy as the last item in the Response flow



10) You are designing an API that allows a consumer to fetch all orders associated to a given
customer. You want to use a RESTful design Which design should you use?

- A. GET/customers/{customer-id)/orders
- B. GET/orders7customer-id={customer-id}
- C. GET /getOrdersForCustomer?customer-id={customer-id}
- D. POST /orders with a post-body of customer-id={customer-id}



11) An API product in Apigee can be used to

- A. restrict access to a set of APIs
- B. configure the quota limits for APIs
- C. restrict access to APIs in different environments
- D. all of the above



12) Which of the following properties have type as "required" in the OpenAPI Specification 2.0?
Choose 3 answers

- A. Info
- B. host
- C. paths
- D. license
- E. swagger
- F. basePath
- G. produces
- H. consumes



13) Where in the proxy should you place a policy that modifies the flow variable target, url?

- A. proxy request flow
- B. proxy response flow
- C. target request flow
- D. target response



14) You need to restrict write access to those applications that have been specifically delegated
that authority by end users Your project is already using OAuth2 for user and app authentication
What should you do?

- A. Create a different URL for updates
- B. Implement user rights checks in the underlying microservice
- C. Use an OAuth2 scope to identify allowed applications and user granted rights
- D. Retrieve user rights from a database by inspecting the user id bound to the OAuth2 token



15) Which is a benefit of using API keys to access API resources?

- A. provides simple mechanism to authenticate developers
- B. provides simple mechanism to identify developer apps
- C. authenticates the developer
- D. authenticates the client application



16) You are using the Apigee ExtractVariables policy JSONPath feature, and discover that the query is not returning the expected result for the payload you are providing What should you do?

- A. Check that the Accept header is set to text/json
- B. Check that the Accept header is set to application/json
- C. Check that the Content-Type header is set to text/json
- D. Check that the Content-Type header is set to application/json



17) Which of the following statements are true for the out-of-the-box Apigee Edge Analytics
Dashboard? Select all that apply Choose 3 answers

- A. Visualize API proxy error metrics
- B. Visualize API traffic metrics
- C. Visualize Developer Engagement metrics
- D. Visualize API Deployment metrics
- E. Visualize Management API traffic metrics



18) Which features are supported in the OAuthV2 policy? Choose 3 answers

- A. Storing of external access tokens
- B. Setting custom attributes for generated access tokens
- C. Credentials validation when password grant_type is used
- D. Setting different expiration for refresh and access tokens



19) In Apigee Quota policy, if it is essential that you do NOT allow any API calls over the quota,
which configuration option is used?

- A. distributed, non-synchronous
- B. non-distributed, synchronous
- C. distnbuted, synchronous
- D. non-distnbuted, non-synchronous



20) Which feature can be used to automatically distribute traffic across multiple target servers'?

- A. use a concurrent rate limiting policy
- B. use a LoadBalancer entry in the HTTPTargelConnection session
- C. use RouteRules with multiple TargetEndpoints
- D. use an AssignMessage policy



21) Which use case best fits the authorization code grant type?

- A. The client app is also the resource owner.
- B. The client app was developed internally and is highly trusted
- C. The client app was developed by an untrusted third party
- D. The client app runs on the end user's browser



22) What is the order in which RouteRules are evaluated when many are present?

- A. no order, random
- B. alphabetical by name
- C. bottom to top as configured in the ProxyEndpoint
- D. bottom to top as configured in the TargetEndpoint
- E. top to bottom as configured in the ProxyEndpoint



23) You are part of an API team working on a versioning strategy for a new API. Several members
of the team disagree on how to indicate the version. According to Apigee recommended practices,
what would you recommend to the team?

- A. Use the payload.
- B. Use a header version: vl.
- C. UsetheURI/products/productjd/vl
- D. UsetheURI/v1/products/product id



24) How many times can an authorization code be used to obtain an access token?

- A. 1
- B. 2
- C. 5
- D. Unlimited until it expires
- E. Unlimited as long as requests come within specified time of authorization code creation



25) Which is a benefit of 2-way TLS (mutual TLS) for target endpoint connections?

- A. Sensitive data presented to end users will be encrypted
- B. Certificates can be used to verify the identity of both Apigee Edge and the target endpoint
- C. End users can use the name of the system to verify that they are connecting to a trusted system.
- D. All of the above



26) In your last release, there were unexpected errors uncovered by users within the first 24
hours The root cause analysis found that key configurations were not promoted to production You
want to avoid similar release failures in your next release What should you do?

- A. Notify operations of the release
- B. Run an automated smoke test suite.
- C. Monitor the logs looking for unusual error patterns
- D. Ensure all sources are checked into source control



27) In Apigee Edge APIs, what is fault.name?

- A. An out-of-the-box variable populated by Edge when a policy fails
- B. Custom variable that you populate before you Raise Fault
- C. System errors that are only populated in script callouts
- D. Backend error names that are populated along with error code
- E. None of the above



28) An API product in Apigee can be used to

- A. restrict access to a set of APIs
- B. configure the quota limits for APIs
- C. restrict access to APIs in different environments
- D. all of the above



29) You are generating tokens depending on a partner's service level at runtime You need to
control access token expiration What should you do?

- A. Pass access token expiration values as a request query parameter
- B. Add expiration times in product custom attributes for retrieval at run time
- C. Store and fetch access token expiration times from a configuration file to use at run time
- D. Store and fetch expiration value from Key Value Maps at runtime to use for access token creation.



30) In which place can outbound SSL be configured?

- A. ProxyEndpoint
- B. HTTPProxyConnection
- C. VirtualHost
- D. TargetEndpoint



31) Which JavaScript statement can be used to raise a fault from a JavaScript policy named
"Weather"?

- A. return-1;
- B. return false;
- C. contextsetVariablefWeather.Fauir, "true*);
- D. throw "Bad Data",



32) Which features are supported in the OAuthV2 policy'?

- A. setting custom attributes for generated access tokens
- B. storing external access tokens
- C. credentials validation when password grantjype is used
- D. setting different expiration for refresh and access tokens



33) Which is true about PostClientFlow?

- A. PostClientFlow will only be executed if a payload is returned.
- B. The response is blocked until! the PostClientFlow policies are executed.
- C. PostClientFlow can only include MessageLogging policies.
- D. PostClientFlow can include any supported policy.



34) Use a Key Value Map when you need to store

- A. a value that expires after 300 seconds
- B. OAuth tokens for backend service calls
- C. configuration values
- D. an HTTP response



35) OAuth 2.0 Resource Owner Password Credentials grant should be used when (Select all that
are correct)

- A. The client applications using this flow are built by 3rd party developers
- B. API resources being protected have context of the resource owner
- C. The client applications using this flow are trusted.
- D. Redirection authentication and authorization flows are required.



36) Which Edge Microgateway plugin does not need to be explicitly added to the plugin sequence
attribute configuration for execution?

- A. analytics
- B. oauth
- C. quota
- D. spike arrest



37) A cloud customer wants to safeguard their APIs against a sudden increase in traffic. You need
to calculate an allowable traffic rate of 100 transactions per second (TPS) What should you do?

- A. Use a default Spike Arrest policy setting the limit to 100 TPS
- B. Use a Quota enforcement policy set to limit throughput to 100 TPS
- C. Use a Spike Arrest policy setting the UseEffectiveCount parameter
- D. Keep a count of accesses in the back-end, rejecting queries when they exceed 100 TPS



38) You need to make multiple target system calls in parallel for a single inbound request The
response should return to the client app as a single object. What should you do?

- A. Use Apigee service callouts
- B. Create route rules for each target endpoint
- C. Create multiple target proxy xmls for each endpoint
- D. Use the Node JS async module to invoke target systems



39) Which are characteristics of the PopulateCache and ResponseCache policies'?

- A. PopulateCache has a TimeOfYear expiry option
- B. PopulateCache allows you to cache any string object.
- C. ResponseCache has separate policy definitions for Lookup vs. Populate cache operations.
- D. ResponseCache caches the complete HTTP response (including headers).



40) If a string value is put in both a cache and a key value map (KVM) using the same key, which
one is true?

- A. The object will expire from both locations after the TimeToLive has passed.
- B. The object will be stored in Cassandra twice
- C. When object is retrieved from KVM, the object with the same key will be returned from the cache instead to improve performance
- D. One of the inserts (either to cache or to KVM) will fail as you cant insert the same key twice



41) What are the HTTPTargetConneclion properties to control network timeouts?

- A. response millis and connect.millis
- B. target.timeout and proxy timeout
- C. connect.timeout.millis and io.timeoutmillis
- D. connect timeout and network timeout



42) Given the following Javascript code snippet, which statement is true?
var paloAlto = httpClient.get{'http://weather.yahooapis.com/forecastrss?w=2467861');
context.session['paloAlto'] = paloAlto;

- A. The code execution will wait for the httpClient to receive a response and store that into a session vanable named paloAlto.
- B. The string paloAlto' will be stored in a message flow variable named paloAlto
- C. The httpClient request will send a POST request to http //weather yahooapis com/forecastrss
- D. The code execution will complete even if the httpClient has not yet received a response



43) As an API Engineer your team has had issues with security vulnerabilities and poor coding
practices in the past. You would like to improve your team's reputation within the organization. What step could take to improve your process?

- A. Add smoke tests to your CI/CD process
- B. Add code quality analysis into your CI/CD process.
- C. Ask the developers to run unit tests prior to code check-in.
- D. Ask the developers to run anti-virus against the code prior to check in.



44) If a custom analytics report needs to filter based on data from the request payload, which
policy would be used?

- A. Custom Report
- B. Message Logging
- C. StatisticsCollector
- D. AssignMessage



45) When is it appropriate to use query parameters in RESTful API design?

- A. When passing username and passwords.
- B. When providing the ability to return different levels of detail in the response.
- C. When requesting that an entire collection be deleted.
- D. When filtering the response based upon a query



46) The product team is rolling out a new reseller program with API's. The product owner has
created Epics covering the high level requirements. The product owner delegate has asked for help
creating a product backlog. What task would the product owner delegate need assistance with?

- A. Creation of a message logging policy in Apigee.
- B. Creating user stories to fulfill the business requirements.
- C. Creating support tickets that cover each of the business requirements.
- D. Creating a cross functional team of API engineers, business analysts, and backend software
  developers.



47) Which protocols are supported by the Message Logging policy?

- A. FTP
- B. HTTP
- C. SCP
- D. TCP
- E. UDP



48) You have a single back end that needs to be exposed to customers using different API request
and response payloads You need to allow these different request types without breaking existing
implementations What should you do?

- A. Create a new API proxy for new customers and invoke backend target system with required
  parameters.
- B. Configure the API as a pass-through proxy and invoke backend target system with client request parameters.
- C. Create a new proxy xml and base path with upgraded version and invoke backend target system with required parameters
- D. Include a new customer requirement in an existing API proxy and invoke backend target system with required parameters.



49) A customer has added response cache policy on all their search APIs, but traffic to the
backend has not reduced much. They ask you to investigate, you find that GET queries are being
cached and available in memory but cache misses are still high You want to improve caching and
reduce cache misses What should you do?

- A. Use lookup/populate cache policy.
- B. Increase the TimeToLive of cached objects
- C. Review key fragments in cache key, ensuring only important parameters are used
- D. Review and increase the number of items on the cache resource to a higher number



50) You are using Apigee Edge as the OAuth Resource Server. The product owner asks you to
create an API that logs a user out by revoking OAuth tokens. What should you do?

- A. Use the InvalidateToken operation with cascade=true
- B. Use the InvalidateToken operation with cascade=false
- C. Store a list of revoked tokens in Firebase. On each access, check the list and only allow unrevoked tokens through
- D. Store a list of revoked tokens in Key-Value Maps. On each access, check the list and only allow unrevoked tokens through



51) What happens to the API request processing when more than one of the conditional flows in
a proxy is matched''

- A. Only the first matching flow from the top runs
- B. Only the first matching flow from the bottom runs.
- C. All matching flows run. in order from the top to the bottom.
- D. A fault is raised and this should be handled in the FaultRules.



52) You have a new set of requirements for a mobile app. The product team has asked for the
following.

>- The app requires access to customer order information
>
>- The app needs to allow a search function for orders by product name

Choose two development tasks that would accomplish the requirements.

- A. Create a new API proxy for a GET /v1/customers/{customerid}/orders
- B. Create a new API proxy for a GET /v1/customers/{customerid}/products/{productname}
- C. The design should include a new custom header X-Product-Name
- D. The Apigee proxy should allow a query parameter for q=
- E. The Apigee proxy should allow a query parameter for orderld=



53) Which policies can be used to create or modify a request message for a service callout?

- A. ServiceCallout
- B. AssignMessage
- C. RequestMessage
- D. Message Validation



54) Which will allow me to set the number of maximum elements to be cached in memory in a
particular cache resource?

- A. Specify in PopulateCache policy
- B. Specify number of Max Elements In Memory on the Cache Resource definition
- C. Specify max size in KB in Cache Resource definition
- D. It cannot be configured as Apigee manages it internally.



55) How can we specrfy the type of Security (basic auth, oauth) used by the API in OpenAPI
Specification 2.0?

- A. specify using the secuntyDefinrtions name property
- B. specify using the secuntySchemas name property
- C. specify using the security Variables name properly
- D. specify using the securityParams name property



56) You need to interact with two different back end systems, depending on inbound request
data One backend is a default target URL without SSL and another one is a dynamic target URL with
2-Way SSL What should you do?

- A. Use service callouts configuration for each target, and use a condition to decide which to invoke.
- B. Use a Key-Value Map configuration to access SSL and URL information to invoke target systems
- C. Use an AssignMessage policy to override default target URL and define 2-way SSL configuration at runtime
- D. Create another target endpoint with SSL configured and define route rule to pick that depends on available variables.



57) You have implemented an Apigee Edge API proxy that includes a new shared flow Before you
release to production, you need to ensure all dependencies are readied What should you do?

- A. Execute static code analysis on the bundle
- B. Run automated unit tests in the test environment
- C. Deploy the shared flow to the production environment
- D. Alert the quality analysis team and product owner that you are preparing the release



58) Which of the following statements are the for Edge Microgateway-aware proxies?

- A. Edge Microgateway-aware proxies must point to a Node js ScnptTarget
- B. Edge Microgateway-aware proxies should at least contain a quota or OAuth2 policy
- C. Edge Microgateway-aware proxies support the execution of conditional flows
- D. Edge Microgateway-aware proxies must use HTTPTargetConnection in the TargetEndpoint



59) When a quota is configured with the distributed flag set to false, the number of which of the
following would affect the overall allowed traffic?

- A. Cassandra nodes
- B. Routers
- C. Message Processors
- D. Management Servers



60) You are implementing several flows in Apigee Edge and realize that there is common
functionality used across many different APIs and flows You want to use Apigee Edge to minimize the number of releases What should you do?

- A. Use a Shared Flow and a Flow Callout where needed.
- B. Use Proxy Chaining and a Service Callout where needed
- C. Use a template build process to compose flows from flow fragments.
- D. Use a Shared Flow and Flow Hooks to enforce all APIs call the shared flow



61) Where can you use a Flow Callout policy? Select all that apply.

- A. Anywhere in a ProxyEndpoint or TargetEndpoint except PostClientFlow
- B. In a Shared Flow
- C. Only in the Flow phase of ProxyEndpoint or TargetEndpoint
- D. Anywhere except another Shared Flow
- E. Only in ProxyEndpoint PreFlow or TargetEndpoint PostFlow



62) Which components of Edge used to configure HTTPS inbound communication?

- A. TLS Store
- B. Virtual Hosts
- C. Certificate Manager
- D. Keystores and Truststores



63) You are working with the product owners and uncover an opportunity for new functionality. The product owners ask you to quickly outline the desired functionality. Which approach should you use?

- A. Use a business requirements document.
- B. Use a behavior driven development "given-when-then" script to outline the main steps
- C. Implement a proof of concept
- D. Implement a suite of unit test cases that qualify a working API



64) Which versioning scheme follows Apigee's API design best practices?

- A. GET /customers/{customend}/v1
- B. GET /customers/v1/{customerid}
- C. GET /v1/customers/{customerid}
- D. GET /customers?customend={customerid}&version=v1



65) Which is a use of OAuth 2 0 scopes'?

- A. govern the level of access for client applications
- B. select API products for the request
- C. implement SAML security assertions
- D. issue OAuth 2 0 refresh tokens



66) The product owner has asked you for a new API. This new API will change a configuration for
a backend system. The use case calls for a single API. Which verb should you use?

- A. GET
- B. PUT
- C. POST
- D. HEAD



67) Which OAuth 2 0 grant requires redirection'?

- A. Authorization Code
- B. Resource Owner Password Credentials
- C. Refresh Token
- D. Client Credentials



68) As an API Engineer your team is deploying code to production tonight. The test team will
spend most of the night ensuring there are no bugs in the new release. After you deploy you want to
go home. What is the best way to quickly verify a complete deployment?

- A. Unit tests
- B. Smoke tests.
- C. Integration tests.
- D. Code quality analysis.



69) In an API Proxy flow we need to orchestrate two XML services - Service A and Service B Data
fields from each response must be returned in the response to the client. Which identifies a set of
Apigee policies that, when used together, could be used to implement this?

- A. AccessEntity,XSL,ExtractVariable
- B. ServiceCallout, ExtractVanable, AssignMessage
- C. XMLToJSON, ExtractVanables, APIKeyValidation
- D. MessageValidation, ServiceCallout, AccessControl



70) As an Apigee API Engineer you attend a meeting where a Product Owner would like to release
a new feature to customers. There are several teams in the meeting, Backend API team, Apigee API
team, and the Security team. The feature will be exposed through the companies external facing
website. The architecture allows the website to call the backend APIs directly. The security team
raises a concern about the backend APIs being wide open to anyone inside the network, not just the
external website. You are later contacted and asked for your teams impacts. How should you reply?

- A. You should recommend an Apigee Edge Access Control policy
- B. You should recommend that the backend API's use TLS v12 to secure their APIs.
- C. You should recommend the use of custom secure headers with time stamp verification
- D. You should recommend a design change that uses a Apigee microgateway in front of the backend APIs.



71) The IT security staff is concerned about unexpected increases in traffic on an API with an
OAuth authorization flow You want to limit impacts of such traffic increases. What should you do?

- A. Use the Spike Arrest policy, to smooth out traffic spikes.
- B. Use a Quota policy, to ensure that no client exceeds a defined quantity of traffic.
- C. Work with operations to ensure autoscaling is set up to respond to traffic events
- D. Use the Concurrent Rate Limit policy, to ensure back-end services are not overwhelmed



72) When using a Shared Flow from a Flow Hook, which proxies will call the Shared Flow?

- A. All proxies in the environment where the Flow Hook is configured
- B. Only proxies that contain a Flow Callout policy
- C. All proxies within an Organization
- D. Only proxies with the Flow Hook checkbox checked



73) Which are NOT a step in the OAuth 2 0 authorization code grant process?

- A. generate an authorization code
- B. generate an access token
- C. verify the device ID
- D. validate the client API key
- E. obtain the end user's consent for the application to request the user's protected resources
- F. validate the developer name



74) The backend API team has asked for metrics on the performance of an API. Which two pieces
of data should you provide?

- A. The internet latency
- B. The network latency
- C. The backend latency
- D. The Apigee proxy latency
- E. The backend database latency



75) What is the purpose of a refresh token?

- A. To extend the amount of time an existing access token can be used by resetting the token's
  expiration time
- B. To request that Apigee re-issue a new access token to replace an expired access token.
- C. To reset an application to its default configuration.
- D. To notify the application that a new access token is required.



76) What does the variable "message.content" represent?

- A. the request content in the request flow
- B. the response content in the response flow
- C. the request content when in the request flow and the response content when in the response flow
- D. neither the request content nor the response content



77) When retrieving a value from an encrypted KVM. the variable name to which it is assigned
must be prefixed with which value to prevent it from being readable in tracing sessions?

- A. encrypted
- B. mask
- C. kvm
- D. private
- E. hidden



78) Which policy can be used to restrict access to API resources based on the client IP?

- A. Regular Expression Protection policy
- B. Basic Authentication policy
- C. Access Control policy
- D. Raise Fault policy



79) Which feature can be used to limit application consumption to a particular group of API proxy
resources?

- A. Virtual host
- B. API product
- C. Developer app
- D. RBAC (Role-based Access Control)



80) As an API Engineer your team would like to make sure you are simulating a user experience
prior to a deployment in a production environment. Which tests should be ran to closely resemble a
consumer interaction with a APIs?

- A. Unit tests
- B. Smoke tests.
- C. Integration tests
- D. Code quality analysis



81) You are composing logs created from user input that contains confidential user information
You need to record the presence of all fields, while ensuring that confidential information is not
recorded in any log Which approach should you take?

- A. Use a data masking configuration to hide sensitive data from logs
- B. Use conditions to skip logging for data that might contain confidential information.
- C. Work with the administrators of the log aggregators to mask confidential data contents.
- D. Sanitize all data on entry using regular expressions that search for confidential data formats.



82) Which statements ate true when the following policy is used?

```xml
<SpikeArrest continueOnError="false" name="Arrest">
<Rate>5ps</Rate>
</SpikeArrest>
```

- A. The rate of 5 per second indicates that 5 requests can be made at any time during a second: but the 6th and later requests dunng the same second would be rejected
- B. Spike arrest is calculated separately on each message processor, so you could end up with
  significantly more than 5 calls in a second
- C. It is possible to make fewer than 5 requests in a second and still cause a spike arrest
- D. The continueOnError setting means that normal flow processing will resume even if the traffic exceeds the spike arrest rate



83) Which describe the ResponseCache' policy in Apigee Edge?

- A. Helps to reduce the response latency
- B. Caches both the request and the response
- C. Helps to reduce the amount of load to the target system.
- D. Should be attached to both Request and Response Flow



84) The AssignMessage policy can be used to

- A. add headers
- B. remove the payload
- C. assign a value to a variable
- D. all of the above



85) You need to access API back-end systems authorized by a machine user credential Each API
call must connect to the API back-end system with unique credentials You need to keep user
credential info securely protected How should you store the credential?

- A. Hardcoded in Assign Message policy
- B. Stored in Key-Value Maps and accessed at run time.
- C. Stored in configuration files and make use at run time.
- D. Stored in encrypted Key-Value Maps and access at run time.



86) Which is the recommended solution lo specify a different backend target for each
environment?

- A. Java/JavaScript/Python callout
- B. TargetEndpoint configuration
- C. RouteRules
- D. TargetServer



87) Which plugins are not enabled as part of the default Edge Microgateway installation?

- A. analytics
- B. oauth
- C. quota
- D. spike arrest



88) While testing your Apigee API proxy, you discover that the TargetEndpomt that is called is not
what you expect. You have verified that there is a RouteRule that matches conditions, and should be
called. What should you do?

- A. Modify the RouteRuies to be more specific.
- B. Create a new proxy for this special condition.
- C. Reorder the RouteRuies so that your condition is found
- D. Add a new TargetServer to handle your specific condition



89) You have a particularly complicated API proxy in Edge., that uses many variables and
conditions You are concerned that the number ot names is quite large, and can become confusing
What can you do?

- A. Use the period "." to namespace variables.
- B. Create a data dictionary describing all the variables
- C. Use camelCase variable names instead of snake_case
- D. Break the proxy into smaller components using proxy chaining



90) What capabilities are provided when using the apigee-access node js module? Select all that
apply

- A. Object caching
- B. Analytics Storage
- C. XML and JSON conversions
- D. Edge flow variable access



91) You are designing an API that allows a consumer to tetch all orders associated to a given
customer You want to use a RESTful design Which design should you use?

- A. GET/customers/{customer-id}/orders
- B. GET/orders?customer-id={customer-id}
- C. GET/getOrdersForCustomer?customer-id={customer-id}
- D. POST /orders with a post-body of customer-id-{customer-id}



92) You are working on a new design for an API. The backend API will set the customer to a
deleted status. The customer will remain in the backend database for later cleanup. The customer
can no longer be retrieved by the API once the status is set. Which method should be used at the
Apigee proxy to set the deleted status?

- A. GET
- B. PUT
- C. POST
- D. DELETE
- E. OPTIONS



93) Which policy can be used to see if a SOAP request matches a provided WSDL?

- A. XMLValidation
- B. MessageValidation
- C. XMLThreatProtection
- D. AccessControl



94) Your company runs their internal services across two datacenters in a hot-warm configuration
The back-end system for a new API runs in both data centers You want to implement a proxy that
handles failover between these data centers What should you do?

- A. Use a Java Callout to the target, calling the hot datacenter first and the warm datacenter in case of failure
- B. Use the not datacenter as the TargetEndpoint and configure a FaultRule to call the warm
  datacenter in case of failure
- C. Create two service callouts to the target, calling the hot datacenter first and the warm datacenter in the event of failure on the first call
- D. Create Target Servers for each datacenter and configuring the TargetEndpoint with a
  LoadBalancer, setting the Is Fallback property on the warm datacenter



95) As an Apigee API Engineer you are building a new proxy as follows:
`POST /v1/virtualnetworkconnections`
The backend developer, being new to RESTful APIs, asks you which response code should be returned
on a successful call. What should you recommend?

- A. Return a 200 OK
- B. Return a 302 Found
- C. Return a 201 Created
- D. Return a 202 Accepted
- E. Return a 303 See Other
- F. Return a 400 Bad Request



96) You are building a new API, and have the choice between several names for a particular field.
According to Apigee recommended practices, what should you do?

- A. Allow each developer to make their own choice.
- B. Use the same field name as in the back end system
- C. Survey typical consumers to determine which common name to use
- D. Pick any name, and make sure that you use that name as consistently as possible



97) You are working on a project that adheres strictly to the Roy Fielding REST concepts. You
need to update a single property named "status" of a complicated entity What should you do?

- A. Fetch the full entity, update the status value locally. DELETE the original entity and POST the new version.
- B. Fetch the full entity. Change only the status value and then send the whole object in the request body of the PUT service
- C. Create a new service that uses the UPDATE verb that accepts the "status* property and updates the entity UPDATE /api/trucks/42/status HTTP/1.1 {status: 5}
- D. Create a new service that uses the PATCH verb designed to update only given fields. PATCH /api/trucks/42 HTTP/1.1 {status: 5}



98) As an API Engineer you get a calendar invite for a backlog grooming session. What should you
expect to take place in the meeting?

- A. Review the Epics and meet the cross functional team
- B. Review and update the user stories and tasks in detail.
- C. Review the high level design document and ask questions
- D. Update support tickets that have been sitting for x number of days



99) Where does a ScriptTarget node.js application run when deployed to Edge?

- A. In a Docker container
- B. Inside a Vert x runtime engine
- C. Google's V8 JavaScript engine
- D. Within Apigee's node.js runtime called Trireme



100) You have a new set of requirements for a mobile app. The product team tells you that the
user data already exists from the website version of the app. The user APIs are currently internal
access only. The product team has asked for the following.

> - The app requires user authentication before data should be accessed.
>
> - The app needs to display user data once the user has logged into the mobile app.
>
> - The app needs to allow changes to user data once the user has logged into the mobile app.

Choose three development tasks that would accomplish the requirements. Choose 3 answers

- A. Create an Oauth 2 0 Service Account API proxy
- B. Create an Oauth 2.0 Password Grant Type API proxy
- C. Create an Oauth 2.0 Client Credential Flow Grant Type API proxy
- D. Create a new API proxy for a GET /v1/customers/{customerid}
- E. Create a new API proxy for a PUT/v1/customers/{customerid}
- F. Create a new API proxy for a POST /v1/customers/{customerid}
- G. Create a new API proxy for a DELETE/v1/customers/{customerid}



101) You are implementing Concurrent Rate Limit, Spike Arrest and Quota policies in your proxy
You want to make sure the simplest checks run first Using the flow shown in the diagram, which
order of operations should you follow?

![image-20201217132507501](C:\Users\davisching\AppData\Roaming\Typora\typora-user-images\image-20201217132507501.png)

- A. Concurrent Rate Limit > Spike Arrest > Quota
- B. Quota > Concurrent Rate Limit > Spike Arrest
- C. Spike Arrest > Quota > Concurrent Rate Limit
- D. Quota > Spike Arrest > Concurrent Rate Limit



102) You are asked by the mobile app team which API should be used to populate a drop down
for regions. According to pragmatic RESTful practices, which method should be used?

- A. GET
- B. HEAD
- C. POST
- D. PUT
- E. OPTIONS



103) As an API Engineer you are approached late in the day with an emergency request to
configure a 2 way TLS connection between Apigee and the backend server. All existing certificates
have been revoked. The security team provides you the PEM file for the backend server and your
new Apigee TLS certificate. What minimum steps are required to reconfigure 2-way TLS?

- A. Add your Apigee TLS certificate to the key store.
- B. Add the backend servers PEM file to the key store.
- C. Add the backend certificate chain to the trust store
- D. Add the backend servers PEM file to the trust store.
- E. Use the KVM to store the contents of the PEM file.
- F. Use the Access Control Policy to reference the PEM file



104) You have the following requirements for your API:

> * Authenticate users.
> * Identify applications.
> * Log update events to StackDriver
> * Enforce quotas based on Product configurations
> * Alert when total API latency exceeds 500 milliseconds

Your Apigee Edge API proxy is currently implemented with the following Proxy Endpoint
configuration

![image-20201217132731371](C:\Users\davisching\AppData\Roaming\Typora\typora-user-images\image-20201217132731371.png)

- A. Authenticate users.
- B. Authenticate applications
- C. Log update events to StackDriver
- D. Enforce quotas based on Product configurations
- E. Alert when total API latency exceeds 500 milliseconds



105) Your project is growing, and there have been several instances of confusion about the naming of data fields. What should you do?

- A. Enforce a naming convention using a static code analysis tool.
- B. Ask developers to do more code reviews, and focus on the names of fields.
- C. Create a basic data dictionary that covers common use cases and rules for naming
- D. Create a unified data model that attempts to model all use cases for each object type



106) You are adding a filter on an API to locate all orders in a specific date range You want to use
a RESTful design Which design should you use?

- A. GET/getOrdersBetweenDates/2016-01-01/2016-02-01
- B. GET/orders'?from-date=2016-01-01&to-date=2016-02-01
- C. GET /customers/{customer-id}/orders'?from-date=2016-01-01&to-date^2016-02-01
- D. POST /searchorders with a post-body of from-date-2016-01-01&to-date=2016-02-01



107) How can we add custom objects to an OpenAPI Specification 2.0?

- A. By prefixing x- before the name of the custom object
- B. By prefixing c- before the name of the custom object
- C. By prefixing ext- before the name of the custom object
- D. Open API specification does not allow custom objects



108) Which statements are true for configuring the Edge Microgateway quota plugin? Choose 3
answers

- A. Quotas are only enforced when client authentication is enabled
- B. Quota plugin requires quota-memory plugin to be installed and enabled
- C. Quota plugin requires OAuth Plugin to be installed and enabled
- D. Quota plugin must execute after the quota-memory Plugin execution
- E. Quota plugin must execute after the OAuth Plugin execution



109) Which describe OAuth 2.0 Refresh Tokens'?

- A. can be used to generate or renew access tokens
- B. is always issued with every access token
- C. can be used to reset crederfflals
- D. may be reused multiple times to create new access tokens



110) When implementing a node js application in Edge, what is the only supported configuration
for running the application?

- A. ScriptTarget in a ServiceCallout policy
- B. ScriptTarget in a TargetEndpotnt
- C. NodeApp policy
- D. ResourceURL in a Javascript policy



111) Your team has the following requirements in building an API:
> * Adhere to Pragmatic REST.
> * Model the API to the consumption use case.
> * Require API Key authentication
> * Implement CORS
> * Validate inputs.

You have begun migrating a SOAP-based web service to a REST API by using the SOAP to REST
function in Apigee Edge Which two of the above-listed requirements could be satisfied by this action?
- A. Adhere to Pragmatic REST
- B. Model the API to the consumption use case
- C. Require API Key authentication
- D. Implement CORS.
- E. Validate inputs.



112) Which approaches can be used for extracting data from a SOAP body and returning it as a
JSON response in Apigee Edge?
Choose 3 answers

- A. Use XSLT to transform the XML payload and then use a XML to JSON policy
- B. Use XML to JSON policy first to convert to JSON and then ExtractVariables with a JSONPath to
  extract the body from the converted SOAP envelope
- C. Use ExtractVariables with an XPath first to extract the SOAP body and then use XML to JSON policy
- D. Use an AssignMessage policy to



113) Your API generates tokens to authenticate users. You have the following requirements
> 1. Limited token lifetime.
> 2. Managed key rotation.
> 3. Self-verifiable content.
> 4. Compact data representation
> 5. Refresh without new challenge.

You plan to use SAML2 Which two of the above-listed requirements are satisfied by using SAML2?
- A. Limited token lifetime.
- B. Managed key rotation
- C. Self-verifiable content
- D. Compact data representation
- E. Refresh without a new challenge



114) Which policy is best used to convert a SOAP response to a JSON response'?

- A. AssignMessage
- B. Message Validation
- C. SOAPToJSON
- D. XMLToJSON



115) Your implementation has the following characteristics
> 1 There are multiple API Products
> 2 Some API proxies are part of more than one API Product.
> 3 Quotas are configured at the API Product level
> 4 In the Quota policy the count, time interval and unit is referenced using Verify API Key flow variables at runtime.
> 5 A single Quota policy is reused across all the products 6. Each app is assigned to exactly one product.

You need to limit the number of requests during weekends for specific products without modifying this design What should you do?
- A. Add custom attributes for counts for every product. Create custom quota policies for every product which references these custom attributes
- B. Set custom attributes for weekday and weekend count at every product Reference these How variables in the count property of Quota policy at runtime
- C. Add custom attributes at the API Product with counts to use for weekdays and weekends. Using flow variables, reference the custom counts in the Quota policy
- D. Add custom attributes for count at Product level Use a JS Policy to determine which count to use in Quota policy at runtime Use this count attribute in the Quota Policy.



116) Which is true about DefaultFaultRule"?

- A. DefaultFaultRule can be used lo handle an error that is not explicitly handled by a FaultRule.
- B. DefaultFaultRules are a fixed set of fault rules defined in the product
- C. You cannot use both FaultRule and DefaultFaultRules in the same API proxy
- D. DefaultFaultRule is used when the fault happens during communication with the target



117) You need to log certain data to a custom logging service while processing the response You
want to avoid processing delays due to logging during the logic flow What should you do?

- A. Use a Node.JS target to implement a nonblocking call.
- B. Attach a Message Logging policy to the Post Client Flow
- C. Implement a Java Extension Callout with a worker thread
- D. Use an asynchronous Service Callout policy in your proxy (low as soon as you have enough data to log



118) Which are techniques for dynamically choosing the target endpoint at runtime?

- A. use routing tables defined for the proxy using the MGMT API
- B. use javascript to set targeturl
- C. use route rules with conditions
- D. nothing is needed, done by default



119) You have a requirement to expose functions and data from an existing back-end system
Using Apigee recommended practices, which step should you take first*?

- A. Write business and functional requirements documents.
- B. Implement ad-hoc microservices using a managed container system.
- C. Catalog the data model of the backing data store or API into a data dictionary
- D. Work with the existing or targeted application consumers to build an Open API Specification
  model



120) What are all of the values typically set in a Raise Fault policy?

- A. Error Code. Reason Phrase, and Payload
- B. Status Code and Payload
- C. Status Code. Reason Phrase, and Payload
- D. Status Code. Error Code, and Payload
- E. None of the above



121) Which is a benefit of three-legged OAuth (authonzation_code grant)'?

- A. authorization codes can be used multiple times to obtain access tokens
- B. allows another individual to access a user's private data
- C. provides access to user-specific resources without exposing end-user credentials to the client application
- D. provides end-user credentials to requesting app



122) Your APIs are configured as a relying party on an OpenID Connect platform. You need to
inspect and verify the OpenID Connect identity. What two actions should you take?

- A. Verify the signature of the JWT using a shared secret.
- B. Parse the JWT to extract the exp: nbf and iat properties to determine if the token is still valid
- C. Pass the JWT to a preconfigured 3rd party for verification of the signature, exp, nbf and iat
  properties
- D. Use the OpenID Connect URL to locate a trusted 3rd party for verification the signature, exp, nbf and iat properties
- E. Using the JKWS URL in the OpenID Connect configuration, fetch the signing key to verify the JWT signature and parameters



123) Which features are supported in the OAuthV2 policy? Choose 3 answers

- A. Storing of external access tokens
- B. Setting custom attributes for generated access tokens
- C. Credentials validation when password grant type is used
- D. Setting different expiration for refresh and access tokens.



124) Which proxy endpoint configuration determines the target endpoint that will be used?

- A. Step
- B. TargetConfiguration
- C. RouteRule
- D. Connection
- E. Path



125) Your client's Apigee Edge configuration uses Local Target Proxy Chaining with proxies that
have monthly quotas The client has noticed that quotas are being exceeded more quickly than
expected What should you do?

- A. Switch to daily quotas.
- B. Disable quota evaluation for chained proxies.
- C. Increase quota levels in API Product settings
- D. Use Analytics reports to balance proxy queries



