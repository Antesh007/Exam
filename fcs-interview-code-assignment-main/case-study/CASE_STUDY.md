# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**
[ A fulfillment system processes inventory through multiple warehouses and stores, making cost allocation more complex than simply recording expenses. The main challenge is ensuring that every operational cost is associated with the correct fulfillment unit while maintaining accuracy, traceability, and scalability.

1. Multiple Cost Categories

A warehouse incurs several independent cost types:

Labor costs
Inventory holding costs
Transportation costs
Packaging costs
Utilities and rent
Equipment maintenance
These costs may not always belong entirely to one warehouse or store and often need to be shared across multiple fulfillment units.

2. Shared Resources

Some resources are shared across locations.

Examples:

One transportation truck delivers inventory to multiple stores.
Regional managers supervise multiple warehouses.
Shared storage facilities.
Corporate overhead.

These costs require allocation rules rather than direct assignment.

Example:

Transportation Cost = ₹10,000

Warehouse A handled 60% of deliveries
Warehouse B handled 40%

Allocated Cost

Warehouse A = ₹6,000

Warehouse B = ₹4,000
3. Inventory Cost Tracking

Inventory costs continuously change because of:

Supplier price changes
Purchase discounts
Currency exchange fluctuations
Damaged inventory
Returns

The system should preserve historical purchase prices instead of replacing old values.

4. Real-Time Cost Updates

Transportation and labor costs frequently change during operations.

Examples:

Fuel price increases
Overtime labor
Emergency shipments
Express delivery charges

The system should support recalculation without affecting historical transactions.

5. Historical Reporting

Managers often need reports such as:

Monthly warehouse operating cost
Cost per order
Cost per product
Cost per shipment
Cost trends over time

Historical records must remain immutable even if allocation rules change later.

Important Design Considerations
Traceability

Every cost should be traceable to its source.
This enables auditing and financial reconciliation.

Scalability

The system should support:

Hundreds of warehouses
Thousands of stores
Millions of products
Millions of transactions

Avoid storing aggregated values that require expensive recalculations.

Accuracy
Financial calculations should avoid floating-point errors.
Use BigDecimal instead of double for all monetary values.
Auditability
Every change should be recorded.
This supports compliance and simplifies investigations.
Performance
Cost reports often involve large datasets.

Consider:

Database indexes
Caching frequently accessed reports
Batch processing
Scheduled aggregation jobs ]

## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**
[
    Cost optimization in fulfillment operations aims to reduce operational expenses while maintaining or improving customer satisfaction and service levels. Rather than focusing only on reducing costs, the objective is to improve overall operational efficiency through better resource utilization, process optimization, and technology adoption.

Potential Cost Optimization Strategies
1. Inventory Optimization

Excess inventory increases storage costs, while insufficient inventory leads to stockouts and delayed deliveries.

Strategies:

Demand forecasting using historical sales data.
Maintain optimal safety stock levels.
Use ABC analysis to prioritize high-value products.
Reduce obsolete inventory through periodic reviews.

Expected Outcomes:

Lower inventory holding costs.
Reduced product wastage.
Improved inventory turnover.
2. Warehouse Optimization

Warehouse operations contribute significantly to fulfillment costs.

Strategies:

Optimize warehouse layout to minimize travel distance.
Implement efficient picking strategies such as zone picking or batch picking.
Use automation for repetitive tasks.
Balance inventory across warehouses based on regional demand.

Expected Outcomes:

Faster order processing.
Reduced labor costs.
Higher warehouse productivity.
3. Transportation Optimization

Transportation is often one of the largest operational expenses.

Strategies:

Optimize delivery routes using route planning algorithms.
Consolidate shipments whenever possible.
Select carriers based on cost and service performance.
Use regional warehouses to reduce shipping distance.

Expected Outcomes:

Lower fuel and transportation costs.
Reduced delivery times.
Improved vehicle utilization.
4. Labor Optimization

Labor costs can fluctuate significantly depending on workload.

Strategies:

Forecast staffing requirements based on demand.
Cross-train employees for multiple roles.
Monitor productivity using operational metrics.
Reduce manual work through automation.

Expected Outcomes:

Lower overtime expenses.
Improved workforce utilization.
Increased operational efficiency.
5. Process Automation

Manual processes often introduce delays and increase operational costs.

Strategies:

Automate inventory updates.
Automate order processing workflows.
Integrate warehouse systems with ERP and transportation systems.
Use barcode or RFID technology for inventory tracking.

Expected Outcomes:

Reduced manual effort.
Fewer operational errors.
Faster processing times.
Identifying Optimization Opportunities

I would first establish measurable operational metrics such as:

Order fulfillment time
Warehouse utilization
Inventory turnover
Transportation cost per shipment
Cost per order
Order accuracy
Return rate
Labor productivity

These metrics help identify bottlenecks and high-cost areas.

For example:

High transportation costs may indicate inefficient routing.
Low inventory turnover may indicate excess stock.
High overtime costs may indicate poor workforce planning.
Prioritizing Improvements

I would prioritize initiatives using an Impact vs. Effort approach.

High Impact, Low Effort
SQL query optimization
Warehouse layout improvements
Shipment consolidation
Inventory threshold tuning
High Impact, High Effort
Warehouse automation
Route optimization systems
Demand forecasting solutions
ERP integration
Low Impact

Implement only after higher-priority initiatives have delivered value.

Implementation Approach
Phase 1 – Assessment
Analyze operational KPIs.
Identify major cost drivers.
Establish baseline metrics.
Phase 2 – Pilot
Implement improvements in a single warehouse or region.
Measure operational improvements.
Gather stakeholder feedback.
Phase 3 – Rollout
Expand successful strategies to all warehouses and stores.
Train operational teams.
Monitor KPIs continuously.
Phase 4 – Continuous Improvement
Review performance regularly.
Adjust strategies based on business growth and seasonal demand.
Use data-driven insights for ongoing optimization.
 ]

## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**
[ 
    A Cost Control Tool is most effective when it is tightly integrated with the organization's financial systems, such as ERP, Accounting, and Procurement systems. Without integration, financial data becomes fragmented, requiring manual reconciliation that increases the risk of errors and delays. Real-time integration enables accurate cost tracking, faster reporting, and better financial decision-making.

Importance of Integration

Integrating the Cost Control Tool with financial systems provides a single source of truth for operational and financial data. It ensures that costs incurred during fulfillment operations—such as inventory purchases, labor expenses, transportation charges, and warehouse overhead—are automatically reflected in financial records.

This eliminates duplicate data entry and ensures consistency between operational systems and accounting records.

Benefits to the Company
1. Real-Time Financial Visibility

Management can monitor operational costs as they occur rather than waiting for end-of-day or month-end reports.

Benefits:

Faster decision-making
Better budget monitoring
Immediate visibility into unexpected cost increases
2. Improved Data Accuracy

Manual data entry often introduces inconsistencies.

Integration ensures that transactions are automatically synchronized between systems, reducing human errors and improving financial accuracy.

3. Faster Financial Reporting

Financial reports can be generated using live operational data instead of manually collecting information from multiple systems.

Examples include:

Cost per warehouse
Cost per store
Cost per product
Transportation expenses
Inventory carrying costs
4. Better Budget Control

By comparing actual operational costs against planned budgets, management can quickly identify overspending and take corrective actions.

5. Regulatory Compliance and Auditing

Integrated systems provide complete audit trails for every financial transaction, making it easier to meet compliance and regulatory requirements.

Each transaction should include:

Transaction ID
Timestamp
Source system
User or service initiating the transaction
Amount
Status
Ensuring Seamless Integration
1. API-Based Integration

The Cost Control Tool should expose REST APIs for communication with ERP and financial systems.

Example:

POST /api/cost-transactions

Whenever a warehouse receives inventory, the Cost Control Tool sends the cost information to the financial system.

2. Event-Driven Architecture

Instead of relying only on synchronous APIs, an event-driven approach improves scalability and reliability.

Example workflow:

Warehouse receives inventory
        ↓
Inventory Updated Event
        ↓
Message Broker (Kafka/RabbitMQ)
        ↓
Financial System
        ↓
General Ledger Updated

This approach decouples systems, allowing them to operate independently while ensuring that financial data is updated asynchronously.

3. Data Validation

Before synchronizing data, validate:

Required fields are present.
Amounts are positive.
Currency is supported.
Product, warehouse, and store identifiers exist.
Duplicate transactions are prevented.

This reduces the risk of invalid financial records.

4. Reliable Synchronization

To maintain consistency:

Use unique transaction identifiers.
Implement retry mechanisms for temporary failures.
Make APIs idempotent to prevent duplicate processing.
Log failed transactions for later reconciliation.
5. Security

Financial information is highly sensitive.

The integration should use:

HTTPS for secure communication.
OAuth 2.0 or JWT for authentication and authorization.
Role-based access control.
Encryption of sensitive financial data.
Audit logging for all financial operations.
Handling Synchronization Failures

Temporary failures are inevitable in distributed systems.

To avoid data loss:

Retry failed requests with exponential backoff.
Store failed messages in a Dead Letter Queue (DLQ) for investigation.
Monitor integration health using dashboards and alerts.
Provide reconciliation processes to identify and correct mismatches between systems
 ]

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**
[ 
    Budgeting and forecasting are essential for efficient fulfillment operations because they help organizations anticipate future costs, allocate resources effectively, and make informed business decisions. Unlike cost tracking, which focuses on historical expenses, budgeting defines financial targets, while forecasting predicts future operational costs based on business trends and expected demand.

A well-designed budgeting and forecasting system enables proactive planning instead of reactive cost management.

Importance of Budgeting and Forecasting
1. Better Financial Planning

Budgets help management allocate funds appropriately across warehouses, stores, transportation, labor, and inventory.

For example, if higher sales are expected during the holiday season, additional budget can be allocated for temporary staff and inventory purchases.

2. Improved Resource Allocation

Forecasting helps determine future requirements such as:

Warehouse capacity
Inventory levels
Transportation resources
Workforce requirements
Equipment utilization

This prevents both over-allocation and under-utilization of resources.

3. Demand Planning

Customer demand is rarely constant.

Forecasting enables organizations to prepare for:

Seasonal demand
Promotional campaigns
Regional demand variations
New product launches
Market growth

This helps avoid stock shortages while reducing excess inventory.

4. Cost Control

By comparing:

Budgeted Cost

vs

Actual Cost

vs

Forecasted Cost

management can quickly identify cost overruns and take corrective actions before they significantly impact profitability.

5. Better Decision Making

Forecasting supports strategic decisions such as:

Opening new warehouses
Expanding storage capacity
Hiring additional staff
Negotiating supplier contracts
Selecting transportation partners
Factors to Consider When Designing the System
1. Historical Data

Accurate forecasting depends on high-quality historical information.

The system should capture:

Sales history
Order volumes
Inventory movement
Transportation expenses
Labor costs
Warehouse operating costs

Historical trends provide the foundation for future predictions.

2. Seasonality

Demand often fluctuates due to:

Holidays
Festivals
Weather
Promotional events
Regional buying patterns

The forecasting model should recognize recurring seasonal trends rather than relying only on average historical values.

3. External Factors

The system should consider variables outside the company's control, such as:

Fuel price fluctuations
Inflation
Supplier price changes
Currency exchange rates
Economic conditions
Supply chain disruptions

These factors can significantly affect future fulfillment costs.

4. Budget Versioning

Budgets frequently evolve during the financial year.

The system should support:

Original budget
Revised budget
Approved budget
Forecast versions

Maintaining version history allows users to compare planning assumptions over time.

5. Flexible Forecast Models

Different forecasting techniques may be appropriate depending on the business context, including:

Historical trend analysis
Moving averages
Year-over-year comparisons
Machine learning models for demand prediction

The system should allow forecasting methods to be configured rather than hardcoded.

6. Scenario Planning

Decision-makers often need to evaluate multiple business scenarios.

Examples:

Best-case scenario
Expected scenario
Worst-case scenario

"What if fuel prices increase by 15%?"

"What if demand grows by 20%?"

"What if a warehouse reaches full capacity?"

Scenario planning enables proactive risk management.

System Design Considerations
Data Integration

Forecasting requires data from multiple systems, including:

ERP
Inventory Management
Warehouse Management System (WMS)
Transportation Management System (TMS)
Financial System

A centralized data platform improves forecast accuracy by ensuring consistent and up-to-date information.

Automation

Forecasts should be generated automatically based on configurable schedules (daily, weekly, or monthly), reducing manual effort and keeping projections current.

Dashboards and Reporting

The system should provide visual insights such as:

Budget vs Actual comparisons
Forecast accuracy over time
Cost trends by warehouse or store
Inventory turnover
Transportation cost trends

Interactive dashboards enable management to quickly identify variances and make informed decisions.

Alerts and Notifications

The system should notify stakeholders when:

Actual costs exceed budget
Forecasts deviate significantly from expectations
Inventory falls below safety stock
Warehouse utilization approaches capacity

Early alerts enable timely corrective actions.
]

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**
[ 

    Replacing a warehouse is not simply a physical relocation; it also has significant financial implications. While the new warehouse may reuse the same Business Unit Code, it represents a new operational entity with its own costs, performance, and budget. At the same time, the historical financial records of the archived warehouse must remain intact for auditing, reporting, and business analysis.

A good cost control strategy should ensure continuity of operations while maintaining clear separation between historical and future financial data.

Why Preserving Cost History is Important
1. Financial Audit and Compliance

Historical cost records should never be overwritten because they support:

Financial audits
Tax reporting
Regulatory compliance
Internal governance

For example, if Warehouse A operated from January to June and was replaced in July, reports for the first half of the year should continue to reflect the costs incurred by the original warehouse.

2. Performance Analysis

Preserving cost history allows the company to compare the performance of the old and new warehouses.

Examples include:

Cost per order
Labor costs
Transportation expenses
Inventory holding costs
Warehouse operating costs

This helps determine whether the replacement has actually improved operational efficiency.

3. Budget Tracking

The archived warehouse has already consumed part of the annual budget.

If historical costs are replaced with the new warehouse's data, management loses visibility into:

Actual spending before replacement
Remaining budget after replacement
Total yearly operational costs

Maintaining separate historical records ensures accurate budget monitoring.

4. Business Continuity

Although the Business Unit Code remains the same for business continuity, the system should distinguish between the archived warehouse and the new warehouse using unique internal identifiers.

For example:

Old Warehouse
ID = WH-001
Business Unit = EAST01
Status = Archived

New Warehouse
ID = WH-125
Business Unit = EAST01
Status = Active

This allows operational systems to continue using the same Business Unit Code while preserving historical records.

Cost Control Considerations During Replacement
Migration Costs

Replacing a warehouse introduces one-time costs such as:

Moving inventory
Transportation
Equipment relocation
Employee training
IT infrastructure setup

These costs should be tracked separately from regular operating expenses to measure the true cost of the transition.

Inventory Transfer

Inventory moved from the old warehouse to the new warehouse should retain its valuation and transaction history.

The system should record inventory transfers rather than treating them as new purchases to avoid inflating inventory costs.

Separate Cost Centers

Although the Business Unit Code is reused, the old and new warehouses should have different internal cost centers or identifiers.

This enables:

Accurate financial reporting
Historical comparisons
Independent performance measurement
Budget Allocation

The budget for the replacement should be divided into:

Transition budget
Operational budget for the new warehouse
Remaining budget from the old warehouse

This separation helps management monitor spending during and after the transition.

Designing the System

A warehouse should never be physically deleted from the system.

Instead:

Archive the old warehouse.
Preserve all historical transactions and cost records.
Create a new warehouse with a new internal identifier.
Reuse the Business Unit Code for operational continuity.
Link the new warehouse to the archived warehouse for reporting purposes.

Example:

Warehouse
-----------
WarehouseId
BusinessUnitCode
Status
ArchivedAt
ReplacedByWarehouseId

This relationship allows reports to show the full lifecycle of a warehouse while keeping financial records immutable.

Keeping the New Warehouse Within Budget

To ensure the replacement remains within budget, the system should:

Establish a separate budget for the new warehouse.
Monitor actual spending against the approved budget.
Track key performance indicators such as cost per order, labor cost, transportation cost, and inventory carrying cost.
Generate alerts when spending approaches predefined thresholds.
Compare actual costs with forecasted costs on a regular basis.

Regular monitoring enables managers to identify overspending early and take corrective actions before budget limits are exceeded.
    
 ]

## Instructions for Candidates
Before starting the case study, read the [BRIEFING.md](BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
