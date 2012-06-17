#!/bin/bash

echo "====== Publishing approval images ======"
for image in imagecomparison/*.png imagediff/*.png; do
	if [ -e "$image" ]; then
		echo "===> $image"
		script/imgur.sh $image
	fi
done