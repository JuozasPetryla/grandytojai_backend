# Deployment

## Backend:
- AWS EC2
- t3.medium instance 2GB RAM 2 CPU cores
- gp3 EBS volume 30GB
- instance user data runs `cd /opt/grandytojai/grandytojai_be/ && nohup ./gradlew bootRun > app.log 2>&1 &` to start the backend app
- Additional env variables for database connection comming from .env
- HTTPS configured

## Database
- AWS RDS
- t4g.micro instance
- Configured with backend having same VPC and subnets, allowing traffic
- Production database password is in Secrets Manager

## Frontend
- AWS Amplify
- Domain created
- HTTPS configured
- No proxy server on production build
- Requests flow: Application Load Balancer (HTTPS) -> EC2 instance (HTTP)

## Scrappers
- Scrappers are in the same EC2 instance, for now they do not run, but can be configured to run on schedule
