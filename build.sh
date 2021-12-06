# build image microservices-users from Docker file

mvn clean install

docker build -f Dockerfile -t azizzakiryarov/microservices-users:latest .

docker push azizzakiryarov/microservices-users:latest

kubectl delete statefulset.apps/microservices-users-deployment service/microservices-users

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-users/deployment.yaml

kubectl apply -f /Users/azizzakiryarov/IdeaProjects/microservices-users/k8s/base/microservices-users/service.yaml