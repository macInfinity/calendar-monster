#!groovy

def MAJOR_VERSION = "1.0"

node {

    def java = tool 'java 8'
    def maven = tool 'maven 3.3.9'

    def RELEASE_NUMBER = "$MAJOR_VERSION.$BUILD_NUMBER"
    def RELEASE_BRANCH = "$JOB_NAME-$RELEASE_NUMBER"

    stage('Checkout') {
        // checkout master
        git "https://github.com/macInfinity/calendar-monster.git"
    }

    stage('Build') {
        withEnv(["JAVA_HOME=$java",
                 "PATH+MAVEN=$maven/bin:${env.JAVA_HOME}/bin"]) {
            try {
                // update version number
                maven "org.codehaus.mojo:versions-maven-plugin:2.3:set -DnewVersion=$RELEASE_NUMBER"

                // build artifact, always look for updates
                // this could be deploy instead of install OR we can push later
                maven "clean install -U"

            } finally {
                junit '**/target/*.xml'

            }
        }
    }

    stage('Tag and Push') {
        git credentialsId: 'macInfinity-github',
                url: 'git@github.com:macInfinity/calendar-monster.git',
                script: "git tag -a $RELEASE_BRANCH -m \"new release candidate\" "

        git credentialsId: 'macInfinity-github',
                url: 'git@github.com:macInfinity/calendar-monster.git',
                script: "git push origin $RELEASE_BRANCH"

    }
}
