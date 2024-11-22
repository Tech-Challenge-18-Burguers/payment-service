variable "regionDefault" {
  default = "us-east-1"
}

variable "projectName" {
  default = "18Burguers"
}

variable "labRole" {
  default = "arn:aws:iam::152722218532:role/LabRole"
}

variable "accessConfig" {
  default = "API_AND_CONFIG_MAP"

}

variable "instanceTypes" {
  default = "t3.medium"
}

variable "principal_arn" {
  default = "arn:aws:iam::152722218532:role/voclabs"
}
variable "policyArn" {
  default = "arn:aws:eks::aws:cluster-access-policy/AmazonEKSClusterAdminPolicy"
}


