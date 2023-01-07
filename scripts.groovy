def incrementVersion() {
    echo "Increasing the app version..."
    sh 'mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
}

def buildJar() {
    echo "Building the application..."
    sh "mvn clean package"
}

def buildImage() {
    echo "Building the docker image..."
    withCredentials([usernamePassword(credentialsId: "docker-hub-credentials", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
        sh "docker build -t $IMAGE_NAME:$IMAGE_VERSION ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push $IMAGE_NAME:$IMAGE_VERSION"
    }
}

def commitChanges() {
    echo "Update Pom.xml file to git repository..."
    withCredentials([usernamePassword(credentialsId: "github-credentials", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {        
        sh 'git config --global user.email "jenkins@gmail.com"'
        sh 'git config --global user.name "jenkins"'

        sh "git status"
        sh "git branch"
        sh "git config --list"

        sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@github.com/deepak-subedi/java-maven-app.git"
        sh "git add ."
        sh 'git commit -m "ci: version bump"'
        sh "git push origin HEAD:main"
    } 
}

def deployApp() {
    echo "Deploying image...."
}

return this