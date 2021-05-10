pipeline {
    agent any
    tools {
        jdk 'jdk11'
        maven 'Maven3.6.3'
    }
    stages {
        stage('test java installation') {
            steps {
                sh 'java -version' 
            }
        }       
        stage('test maven installation') {
            steps {
                sh 'mvn -version'
            }
        }
    }
} 