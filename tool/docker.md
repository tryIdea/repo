#### alisql docker连接
1、docker pull alisql/alisql
2、docker run --name yunli-alisql -e MYSQL_ROOT_PASSWORD=123456 -d -p3306:3306 alisql/alisql
3、或者 docker run --name yunli-alisql -e MYSQL_ROOT_PASSWORD=123456 -d -P alisql/alisql
4、mycli -uroot -p123456

#### redis docker连接
1、docker run --name yunli-redis -d  -p6379:6379 redis
2、docker exec -it yunli-redis redis-cli
