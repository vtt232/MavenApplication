FROM ubuntu:latest
LABEL authors="vtthinh"

ENTRYPOINT ["top", "-b"]