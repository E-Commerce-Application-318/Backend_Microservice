# APIs:

## auth
### localhost:8081
/auth/login
/auth/register
/user/index \
/user/update \
/user/location/update

## product:
### localhost:8082
GET /product/all-products
GET /product/product-detail/{productId}
GET /product/shop/{shopId}/all-products
POST /product/shop/{shopId}/add-product
PUT /product/shop/{shopId}/update-product
DELETE /product/shop/{shopId}/delete-product/{productId}

/product/search="" & page=1 & pageSize = 1 & sortBy="" & orderBy="asc" & minPrice=0 & maxPrice= 10000 \

## shop: create shop when user create account with seller option
### localhost:8083
shop/update \
shop/product/index: list all product in shop \
shop/product?productId: information of a specific product \
shop/product/create: create product in shop \
shop/product/update?productId: update product in shop



### cart:
cart/add \
cart/update productId quantity \
cart

### order:
order/create \
order/update \
order/confirmed

### End