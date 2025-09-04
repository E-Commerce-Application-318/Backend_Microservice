# APIs:

## auth
### localhost:8081
/auth/login
/auth/register
/user/index \
/user/update \
/user/location/update

## shop: create shop when user create account with seller option
### localhost:8082
shop/update \
shop/product/index: list all product in shop \
shop/product?productId: information of a specific product \
shop/product/create: create product in shop \
shop/product/update?productId: update product in shop

## product:
### localhost:8083
/product/index \
/product/search="" & page=1 & pageSize = 1 & sortBy="" & orderBy="asc" & minPrice=0 & maxPrice= 10000 \
/product/id=?

### cart:
cart/add \
cart/update productId quantity \
cart

### order:
order/create \
order/update \
order/confirmed

### End