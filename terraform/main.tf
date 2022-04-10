resource "aws_instance" "bh-instance" {
  ami = var.linux_2_ami
  instance_type = var.t2_micro_instance_type

  user_data = file(var.docker_user_data)

  tags = {
    Name = var.instance_name
  }
}