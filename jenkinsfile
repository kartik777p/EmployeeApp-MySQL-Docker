pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "employee-app"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out branch deploy..."
                git branch: 'deploy', url: 'https://github.com/kartik777p/EmployeeApp-MySQL-Docker.git'
            }
        }

        stage('Build Jar') {
            steps {
                echo "Building jar using Maven wrapper..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker images using docker-compose..."
                sh 'docker-compose build'
            }
        }

        stage('Deploy Application') {
            steps {
                echo "Stopping previous containers..."
                sh 'docker-compose down'
                echo "Starting containers..."
                sh 'docker-compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "Checking app health endpoint..."
                script {
                    def appUrl = "http://localhost:8082/actuator/health"
                    def response = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" ${appUrl}", returnStdout: true).trim()
                    if (response == "200") {
                        echo "Application deployed successfully!"
                    } else {
                        error "Application deployment failed with status code: ${response}"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully."
        }
        failure {
            echo "Pipeline failed. Please check the logs."
        }
    }
}
