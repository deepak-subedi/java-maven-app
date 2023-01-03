def buildJar() {
    echo "Building the application..."
    sh "mvn package"
}

def buildImage() {
    echo "Building the docker image..."
    withCredentials([usernamePassword(credentialsId: "docker-hub-credentials", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
        sh "docker build -t $IMAGE_NAME:$IMAGE_VERSION ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push $IMAGE_NAME:$IMAGE_VERSION"
    }
}

def deployApp() {
    echo "Deploying image..."
}

return this