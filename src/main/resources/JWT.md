JJWT separates its functionality:

jjwt-api
↓
Interfaces / API we write code against

jjwt-impl
↓
Actual JWT implementation

jjwt-jackson
↓
JSON serialization/deserialization


=====================================API Verification=====================================
Test 1 — Admin JWT

Use the same admin JWT:

GET http://localhost:8080/admin
Authorization: Bearer <admin-token>

Expected:

200 OK
Welcome Admin
Test 2 — Remove the token

Send:

GET http://localhost:8080/admin

with no Authorization header.

Expected:

401 Unauthorized
Test 3 — Admin JWT on payments
GET http://localhost:8080/payments
Authorization: Bearer <admin-token>

Expected:

200 OK
Payment details