variable "access_key" {
  type = string
}

variable "secret_key" {
  type = string
}

variable "region" {
  default = "eu-central-1"
}

variable "linux_2_ami" {
  default = "ami-0dcc0ebde7b2e00db"
}

variable "t2_micro_instance_type" {
  default = "t2.micro"
}

variable "docker_user_data" {
  default = "user_data/docker.sh"
}

variable "instance_name" {
  default = "Broken Hammer"
}