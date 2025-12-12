#  Digital Gold Purchase Flow  (Backend Assignment)

This project implements a simplified backend for a digital gold purchase workflow.  
It simulates three systems:

1. **Simplify API** – Handles orders, payments, and gold allocation  
2. **Partner API** – Provides mock gold price  
3. **Payment Gateway API** – Simulates payment creation + webhook callbacks  

Database: **MySQL**  
Framework: **Spring Boot 4 (Spring 7 stack)**

---

## 2. Digital Gold Flow 

### Step 1 — Fetch Gold Price  
Simplify backend fetches price from Partner API.

### Step 2 — Create Order  
User enters amount → price locked → GST and grams calculated.

### Step 3 — Create Payment Session  
User selects method: UPI / CARD / NETBANKING.  
Mock gateway returns redirect URL.

### Step 4 — Payment Simulation  
Gateway sends webhook to Simplify backend (SUCCESS).

### Step 5 — Gold Allocation  
Amount converted → grams → saved to DB.

---

## 4. API Endpoints

### Partner API
GET /simplify/price


### Simplify API
POST /simplify/order<br>
POST /simplify/order/{orderId}/pay<br>
POST /simplify/payment/confirm<br>
GET /simplify/allocations/{userId}<br>


### Payment Gateway (Mock)
POST /payment/simulate

---

## 5. Complete POSTMAN Flow

### STEP 1 — Get Gold Price

**GET**
http://localhost:8080/simplify/price

**Response**
{<br>
  "pricePerGram": 13500<br>
}<br>
### STEP 2 — Create Order
**POST**
http://localhost:8080/simplify/order<br>
**Body**
{<br>
  "userId": "user1",<br>
  "amount": 10<br>
}<br>
**Response**
{<br>
  "orderId": "ORD-17012345",<br>
  "gstAmount": 0.30,<br>
  "goldValue": 9.70,<br>
  "grams": 0.0007185,<br>
  "pricePerGram": 13500,<br>
  "priceLockExpiry": "2025-12-12T12:30:00"<br>
}<br>
### STEP 3 — Create Payment Session
**POST**
http://localhost:8080/simplify/order/ORD-17012345/pay<br>
**Body (Choose one):**<br>
**UPI**
{<br>
  "amount": 10,<br>
  "method": "UPI"<br>
}<br>
**CARD**
{<br>
  "amount": 10,<br>
  "method": "CARD"<br>
}<br>
**NETBANKING**
{<br>
  "amount": 10,<br>
  "method": "NETBANKING"<br>
}<br>
**Response**
{<br>
  "paymentId": "PAY-17099911",<br>
  "redirectUrl": "https://mock-gateway.com/pay/upi/PAY-17099911"<br>
}<br>
### STEP 4A — Simulate Payment SUCCESS (Webhook)
**POST**
http://localhost:8080/payment/simulate<br>
**Body**<br>
{<br>
  "paymentId": "PAY-17099911",<br>
  "orderId": "ORD-17012345",<br>
  "amount": 10,<br>
  "merchantCallbackUrl": "http://localhost:8080/simplify/payment/confirm"<br>
}<br>
**Response**
{<br>
  "paymentId": "PAY-17099911",<br>
  "orderId": "ORD-17012345",<br>
  "status": "SUCCESS",<br>
  "paymentRef": "GWREF-AB12CD34",<br>
  "amountInINR": 10,<br>
  "timestamp": "2025-12-12T12:35:45Z"<br>
}
### STEP 4B — Merchant Callback (Automatic Webhook)

When you call:

**POST**
http://localhost:8080/payment/simulate<br>

The Payment Gateway automatically sends a webhook to the merchant callback URL:

**POST**
http://localhost:8080/simplify/payment/confirm

**Sample Webhook Body Received by Simplify Backend**
**Response**
{<br>
  "paymentId": "PAY-17099911",<br>
  "orderId": "ORD-17012345",<br>
  "status": "SUCCESS",<br>
  "paymentRef": "GWREF-AB12CD34",<br>
  "amountInINR": 10,<br>
  "timestamp": "2025-12-12T12:35:45Z"<br>
}<br>
Simplify backend then:<br>

Updates payment status<br>

Allocates gold grams to user<br>

Stores allocation in MySQL<br>

Returns success response<br>

{<br>
  "message": "Payment verified and gold allocated successfully",<br>
  "allocatedGrams": 0.0007185<br>
}<br>
### STEP 5 — Get Allocation for User<br>
**GET**
http://localhost:8080/simplify/allocations/user1
**Response**

  {<br>
    "allocationId": "ALLOC-170123",<br>
    "orderId": "ORD-17012345",<br>
    "grams": 0.0007185,<br>
    "pricePerGram": 13500,<br>
    "timestamp": "2025-12-12T12:35:45Z"<br>
  }<br>

### 6. How to Run
mvn spring-boot:run<br>

**MySQL Configuration**
spring.datasource.url=jdbc:mysql://localhost:3306/smassignmentdb<br>
spring.datasource.username=root<br>
spring.datasource.password=yourpassword<br>
spring.jpa.hibernate.ddl-auto=update<br>
