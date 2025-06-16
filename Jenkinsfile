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
                git branch: 'deploy', url: 'https://github.com/kartik777p/EmployeeApp-MySQL-Docker.git'
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
                // If you want to force a clean build without cache:
                // sh 'docker-compose build --no-cache'
            }
        }

        stage('Deploy Application') {
            steps {
                echo "Deploying application with docker-compose up..."
                // Stop existing running services
                sh 'docker-compose down'
                // Start new services in detached mode
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Deployment Failed. Please check the logs.'
        }
    }
}
