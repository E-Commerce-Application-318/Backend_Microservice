# APIs:

## auth
### localhost:8081
GET /auth/login done \
POST /auth/register done \
GET /user/index  \

## product:
### localhost:8082
GET /product/all-products done
GET /product/product-detail/{productId} done
GET /product/shop/{shopId}/all-products done
POST /product/shop/{shopId}/add-product done
PUT /product/shop/{shopId}/update-product
DELETE /product/shop/{shopId}/delete-product/{productId}
GET /product/search="" & page=1 & pageSize = 1 & sortBy="" & orderBy="asc" & minPrice=0 & maxPrice= 10000 \

## shop: create shop when user create account with seller option
### localhost:8083

GET shop/{shopId}/shop-detail done \
get detial information of shop
GET shop/{shopId}/shop-details/product done \
get all products in shop + shop detail information

## cart:
### localhost:8085
GET cart/{userId}/get-cart-items done \
gett cart detail + all items that added to cart before

POST cart/{userId}/add-cart-items \
add product to cart

UPDATE cart/{userId}/product/{productId}
update product in cart (increase/decrease quantity)

DELETE cart/{userId}/product/{productId} \
delete product in cart (incase product reduce from 1 - 0) or click remove

## order:
### localhost:8084
POST order/{userId}/create
create order from chosen product of cart with pending status

PUT order/update/{orderId}/status \
update order status (pending -> confirmed)

PUT order/update/{orderId} \
update order locaiton, 

REMOVE
DELETE order/{orderId}/delete \
delete order, refund quantity of product in order 


### agent:
### localhost:8086

check the agent controller
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Hi, when does my booking begin?"

### get all orders of user
List all orders already have
```bash
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Get all orders. My user ID is 11111111-1111-1111-1111-111111111111"
```

curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Get all orders"

### update order for user
```bash
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Update the order. My address is 18 Kenny Street"
```

###
```bash
curl -G "http://localhost:8086/agents" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=Get all orders. My user ID is 11111111-1111-1111-1111-111111111111"
```

create order -> call cart service to check items in cart, update stock number by product service
delete order -> call product service to re-stock the items in order
update order -> update information of order like address, phone, number
### End