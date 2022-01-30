# build image microservices-users from Docker file

mvn clean install

docker build -f Dockerfile -t azizzakiryarov/microservices-users:latest .

docker push azizzakiryarov/microservices-users:latest

kubectl delete deployment.apps/mysql service/mysql-service

kubectl delete statefulset.apps/microservices-users-deployment service/microservices-users

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-mysql-db/deployment.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-mysql-db/service.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-mysql-db/persistent-volume.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-mysql-db/persistent-volume-claim.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-users/deployment.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-users/service.yaml