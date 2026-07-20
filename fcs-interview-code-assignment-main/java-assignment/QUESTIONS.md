# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
YES, I would refactor to standardize on Strategy #3 (WarehouseRepository pattern) for the following reasons:

Problems with Current Approach:
Inconsistency -

Store uses active record, Product uses empty repository, Warehouse uses port pattern
Makes codebase hard to maintain and understand
Developers don't know which pattern to follow

Tight Coupling (Store class) - 

Store extends PanacheEntity directly = tight coupling to Panache
Hard to test business logic without database
Cannot swap implementations

Missing Domain Abstraction -

Store and Product have no domain ports/interfaces
Product uses PanacheRepository directly (framework coupling)
Warehouse correctly abstracts domain via WarehouseStore port

Testability Issues-

Store direct PanacheEntity = cannot mock database
Product tight to Panache = hard to test in isolation
Warehouse pattern allows mocking via WarehouseStore interface
	
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
This is a classic "it depends" situation, and honestly, I think the team is doing it right with Warehouse.

Why OpenAPI for Warehouse made sense:

The business clearly knew what they wanted upfront (warehouse operations are well-defined)
Having the spec means Product/frontend teams can see exactly what they're getting
It forces you to think about API design BEFORE you code it (which prevents a lot of mistakes)
Why manual coding for Product/Store is actually okay:

They're simple CRUD endpoints. There's nothing fancy here.
Writing 3 methods per resource isn't going to kill anyone
The team knows exactly what the API does by reading the code
For small teams, the overhead of OpenAPI tooling might not be worth it
My honest complaint about OpenAPI:

It's another file to maintain
The YAML gets out of sync with code if you're not careful
The generated interfaces in Warehouse? Half the team probably doesn't even know they exist
Sometimes you just want to code and move on
What I'd actually recommend: For THIS project with THIS complexity? Manual coding is fine.

But if we were building this for real:

Get OpenAPI specs for everything
Why? Because when the frontend team asks "what fields does a Warehouse have?", they shouldn't have to read Java code
Because when Product marketing wants to understand the API, they need a spec they can read
Because if we ever have to support multiple versions or build client libraries, OpenAPI saves us
The compromise I'd take: Keep manual coding, but add annotations (@OpenAPI) and auto-generate the specs at build time. Best of both worlds.
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
If I had 4 hours to test this, here's what I'd actually do:

Hour 1: The validation tests

Test all the warehouse constraints because those are the business logic
Test the 3 fulfillment constraints because they're complex and easy to mess up
Skip testing basic getters/setters - that's a waste of time
Hour 1.5: The happy path

Create a warehouse, retrieve it, archive it - make sure the flow works
Create a fulfillment unit and make sure it validates correctly
Store create triggers legacy system - does it actually happen? Test that.
Hour 1: Repository tests

Do queries actually work?
Does the database persist data correctly?
Skip testing Panache itself - that's tested by Quarkus team
Hour 0.5: Setup coverage

Add JaCoCo, set 80% threshold, move on
What I WON'T do:

Test getters/setters (pointless)
Mock repositories (then what am I testing?)
Test generated code (trust the framework)
Achieve 100% coverage (diminishing returns after 85%)
The truth about testing:

80% of bugs live in 20% of the code (the business logic)
Test that 20% thoroughly
Don't waste time on boilerplate
A fast test suite you run often beats a slow one you avoid
Honest coverage targets:

UseCase logic: 95% (this is where bugs hide)
Repositories: 80% (test the queries, trust Panache)
REST endpoints: 60% (honestly, if your usecases are tested, endpoints usually work)
Generated code: 0% (why bother?)
What kills test suites:

Testing implementation details instead of behavior
100% coverage obsession
Tests that are brittle (break when you refactor)
Mocking too much (you end up testing mocks, not code)