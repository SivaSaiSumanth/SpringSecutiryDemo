                    SecurityFilterChain
                           │
                           ▼
Request ──────────> Spring Security
│
Is authenticated?
/           \
NO             YES
↓               ↓
Login Page       Controller
│
▼
/hello

1. Users & credentials
   Username	Password	Roles	Authorities
   user	user123	ROLE_USER	PAYMENT_READ
   admin	admin123	ROLE_ADMIN	PAYMENT_READ, PAYMENT_CREATE, PAYMENT_DELETE
   payment	payment123	None	PAYMENT_CREATE

Remember: .roles("ADMIN") internally creates ROLE_ADMIN.

2. APIs and expected behavior

Your controller currently has:

/hello
GET http://localhost:8080/hello

Security rule:

.requestMatchers("/hello").authenticated()
User	Expected
user/user123	✅ 200
admin/admin123	✅ 200
payment/payment123	✅ 200
Wrong password	❌ 401
No credentials	❌ 401

Why?

Any authenticated user can access /hello.

/admin
GET http://localhost:8080/admin

Security rule:

.requestMatchers("/admin").hasRole("ADMIN")
User	Expected
user/user123	❌ 403
admin/admin123	✅ 200
payment/payment123	❌ 403
Wrong password	❌ 401
No credentials	❌ 401

Why?

Only:

ROLE_ADMIN

is allowed.

Your admin user gets ROLE_ADMIN from:

.roles("ADMIN")
/payments
GET http://localhost:8080/payments

Security rule:

.requestMatchers("/payments").hasAuthority("PAYMENT_READ")
User	Expected
user/user123	✅ 200
admin/admin123	✅ 200
payment/payment123	❌ 403
Wrong password	❌ 401
No credentials	❌ 401

Why?

This endpoint doesn't care about the user's role.

It specifically requires:

PAYMENT_READ

So:

user
└── PAYMENT_READ ✅

admin
└── PAYMENT_READ ✅

payment
└── PAYMENT_READ ❌
3. Complete Postman test matrix

This is the one I'd keep in your interview notebook:

API	user	admin	payment
GET /hello	✅ 200	✅ 200	✅ 200
GET /admin	❌ 403	✅ 200	❌ 403
GET /payments	✅ 200	✅ 200	❌ 403

And for wrong password/no credentials:

401 Unauthorized

For an authenticated user who lacks the required permission:

403 Forbidden
4. The complete mental model

Your current application is essentially:

                         Spring Security
                              │
                              ▼
                       Authentication
                              │
                 ┌────────────┴────────────┐
                 │                         │
              username                  password
                 │                         │
                 └────────────┬────────────┘
                              ▼
                     UserDetailsService
                              │
                              ▼
                     PasswordEncoder
                              │
                              ▼
                       Authentication
                         successful
                              │
                              ▼
                        Authorization
                              │
          ┌───────────────────┼───────────────────┐
          ▼                   ▼                   ▼
       /hello              /admin             /payments
          │                   │                   │
authenticated()       hasRole("ADMIN")   hasAuthority("PAYMENT_READ")
│                   │                   │
▼                   ▼                   ▼
Anyone              admin only       user + admin
The three most important outcomes
401 → Authentication failed
"Who are you?"

403 → Authentication succeeded,
but permission is insufficient
"You are authenticated, but you can't access this."

200 → Authentication + authorization succeeded