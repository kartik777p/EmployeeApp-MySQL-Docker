pipeline {
    agent any

    environment {
        // Docker image name (adjust as needed)
        DOCKER_IMAGE = "employee-app"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Getting code from branch deploy..."
                git branch: 'deploy', url: 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Build Jar') {
            steps {
                echo "Building jar with Maven..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building docker images using docker-compose..."
                sh 'docker-compose build'
            }
        }

        stage('Deploy Application') {
            steps {
                echo "Deploying application with docker-compose up..."
                sh 'docker-compose down'
                echo "After docker-compose down"
                sh 'docker ps'
                echo "Before docker-compose up "
                sh 'docker-compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "Checking if application is up and running..."
                script {
                    // Adjust port and healthcheck URL as needed
                    def appUrl = "http://localhost:8082/actuator/health"
                    def response = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" ${appUrl}", returnStdout: true).trim()
                    if (response == "200") {
                        echo "Application deployed successfully and health endpoint is reachable!"
                    } else {
                        error "Application deployment failed! Health check returned status: ${response}"
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'Build or deployment failed! Check logs.'
        }
        success {
            echo 'Pipeline finished successfully.'
        }
    }
}
