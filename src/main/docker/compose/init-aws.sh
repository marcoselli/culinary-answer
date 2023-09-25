### TABLES ###
# - UserDetail
awslocal dynamodb create-table --table-name userDetailTable --attribute-definitions AttributeName=userDetailId,AttributeType=S --key-schema AttributeName=userDetailId,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
awslocal dynamodb update-table --table-name userDetailTable --attribute-definitions AttributeName=username,AttributeType=S --global-secondary-index-updates "[{\"Create\":{\"IndexName\":\"username-index\",\"KeySchema\":[{\"AttributeName\":\"username\",\"KeyType\":\"HASH\"}],\"ProvisionedThroughput\":{\"ReadCapacityUnits\": 5,\"WriteCapacityUnits\": 5},\"Projection\":{\"ProjectionType\":\"ALL\"}}}]"

# - CulinaryTopics
awslocal dynamodb create-table --table-name questionAnsweredTable --attribute-definitions AttributeName=questionId,AttributeType=S --key-schema AttributeName=questionId,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
awslocal dynamodb update-table --table-name questionAnsweredTable --attribute-definitions AttributeName=requesterId,AttributeType=S --global-secondary-index-updates "[{\"Create\":{\"IndexName\":\"requesterId-index\",\"KeySchema\":[{\"AttributeName\":\"requesterId\",\"KeyType\":\"HASH\"}],\"ProvisionedThroughput\":{\"ReadCapacityUnits\": 5,\"WriteCapacityUnits\": 5},\"Projection\":{\"ProjectionType\":\"ALL\"}}}]"

### SECRETS MANAGER ####
awslocal secretsmanager create-secret --name dev/open-ai/secrets --secret-string '{ "apiToken": "YOUR_API_TOKEN",
                                                                                    "maxTokens": 2048,
                                                                                    "modelType": "text-davinci-003"
                                                                                  }'

awslocal secretsmanager create-secret --name dev/keycloak/secrets --secret-string '{
                                                                                    "realmName": "culinary-answer",
                                                                                    "adminClientUsername": "backend-service",
                                                                                    "adminClientPassword": "secret"
                                                                                  }'
