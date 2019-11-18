printf "
http.port=\"80\"
sirius.autoSetup=true
http.sessionSecret=\"TEST\"
client.server=\"localhost:80\"
product.baseUrl=\"http://localhost:80\"
redis.pools.system.host=\"$REDIS_HOST\"
redis.pools.system.port=\"$REDIS_PORT\"
elasticsearch {
    hosts=\"$ES_HOST\"
}" >instance.conf
java -cp sirius-chat-sample-DEVELOPMENT-SNAPSHOT.jar:"release-dir/lib/*" sirius.kernel.Setup
