#!groovy

def MAJOR_VERSION = "1.0"

node {

    def java = tool 'java 8'
    def maven = tool 'maven 3.3.9'

    def RELEASE_NUMBER = "$MAJOR_VERSION.$BUILD_NUMBER"
    def RELEASE_TAG = "$JOB_NAME-$RELEASE_NUMBER"

    stage('Checkout') {
        checkout scm
    }

    stage('Build and Test') {
        withEnv(["JAVA_HOME=$java",
                 "PATH+MAVEN=$maven/bin:${env.JAVA_HOME}/bin"]) {
            try {
                // update version number
                sh "mvn org.codehaus.mojo:versions-maven-plugin:2.3:set " +
                        "-DnewVersion=$RELEASE_NUMBER"

                // build artifact, always look for updates
                // this could be deploy instead of install OR we can push later
                sh "mvn clean install -U"

            } finally {
                junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

            }
        }
    }

    // https://github.com/dalalv/jenkinsfiles/blob/master/jenkinsfile-push-to-git-repo
    stage('Tag and Push') {
        withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                          credentialsId   : 'macInfinity-github',
                          usernameVariable: 'GIT_USERNAME',
                          passwordVariable: 'GIT_PASSWORD']]) {
            sh "git tag -a $RELEASE_TAG -m \"new release candidate\""
            sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@" +
                    "github.com/macInfinity/calendar-monster.git $RELEASE_TAG"
        }
    }

}

