#!/bin/sh
printf "http.port=80
sirius.autoSetup=true
http.sessionSecret=\"S3cret\"
client.server=\"localhost:80\"
product.baseUrl=\"http://localhost:80\"
redis.pools.system.host=\"%s\"
redis.pools.system.port=%s
elasticsearch.hosts=\"%s\"
" "$REDIS_HOST" "$REDIS_PORT" "$ES_HOST" >instance.conf

java -cp sirius-chat-sample-DEVELOPMENT-SNAPSHOT.jar:"release-dir/lib/*" sirius.kernel.Setup
