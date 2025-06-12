pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "employee-app"
        COMPOSE_FILE = "docker-compose.yml"
        HEALTH_CHECK_URL = "http://localhost:8082/actuator/health"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out branch 'deploy'..."
                git branch: 'deploy', url: 'https://github.com/kartik777p/EmployeeApp-MySQL-Docker.git'
            }
        }

        stage('Build Jar') {
            steps {
                echo "Building Spring Boot jar using Maven..."
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Images') {
            steps {
                echo "Building Docker images using docker-compose..."
                sh 'docker-compose build'
            }
        }

        stage('Cleanup Existing Containers') {
            steps {
                echo "Stopping and removing previous containers..."
                // Down containers and forcibly remove any conflicting ones
                sh 'docker-compose down --remove-orphans || true'
                sh '''
                    docker rm -f mysql_db_docker phpmyadmin employee-app sonarqube sonar-db || true
                '''
            }
        }

        stage('Deploy Application') {
            steps {
                echo "Starting containers with docker-compose..."
                sh 'docker-compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "Waiting for application to start and verifying health check..."
                script {
                    def maxRetries = 5
                    def waitSeconds = 5
                    def success = false

                    for (int i = 0; i < maxRetries; i++) {
                        def status = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${HEALTH_CHECK_URL}", returnStdout: true).trim()
                        if (status == '200') {
                            echo "✅ Application is healthy and running!"
                            success = true
                            break
                        } else {
                            echo "⏳ Attempt ${i+1}: Not ready (Status: ${status}). Retrying in ${waitSeconds}s..."
                            sleep waitSeconds
                        }
                    }

                    if (!success) {
                        error "❌ Application did not become healthy after ${maxRetries} attempts."
                    }
                }
            }
        }
    }

    post {
        success {
            echo "🎉 Deployment pipeline completed successfully!"
        }
        failure {
            echo "🚨 Deployment pipeline failed. Check logs above for details."
        }
    }
}
