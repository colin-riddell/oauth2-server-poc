docker run --rm -it \
  --network hydraguide \
  -p 9010:9010 \
  oryd/hydra:v1.10.6 \
  token user --skip-tls-verify \
    --port 9010 \
    --auth-url https://auth-server:9000/login \
    --token-url https://auth-server:9000/oauth2/token \
    --client-id clientid \
    --client-secret client-secret \
    --scope openid,read
