#!/bin/bash

# This shell script generates automatically a weekly report about the activities in the Git repository.
if [ $# -eq 0 ]
  then
    echo "Usage: ./report.sh <author>"
    exit 1
fi

git log --author=$1 --since='1 week ago' --until='now'
