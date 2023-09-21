awslocal secretsmanager create-secret --name dev/open-ai/secrets --secret-string '{ "apiToken": "YOUR_API_TOKEN",
                                                                                    "maxTokens": 2048,
                                                                                    "modelType": "text-davinci-003"
                                                                                  }'

awslocal secretsmanager create-secret --name dev/keycloak/secrets --secret-string '{
                                                                                    "realmName": "culinary-answer",
                                                                                    "adminClientUsername": "backend-service",
                                                                                    "adminClientPassword": "secret"
                                                                                  }'
