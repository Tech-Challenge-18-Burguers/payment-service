terraform {
  backend "s3" {
    bucket = "18burguerstfstate"
    key    = "PaymentService/terraform.tfstate"
    region = "us-east-1"
  }
}