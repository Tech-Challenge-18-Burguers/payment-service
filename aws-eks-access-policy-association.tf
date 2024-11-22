resource "aws_eks_access_policy_association" "aws-eks-access-policy-association" {
  cluster_name  = aws_eks_cluster.eks-cluster.name
  policy_arn    = var.policyArn
  principal_arn = var.principal_arn

  access_scope {
    type = "cluster"
  }

}