javac cdac/in/gate/result/ResultProcessing.java

java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/GG/key.csv -r ../papers/GG/response.csv -sa -ap ../papers/GG/applicant.csv > ../result/GG/section-data.csv
echo "GG done!"
java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/XE/key.csv -r ../papers/XE/response.csv -sa -av -ap ../papers/XE/applicant.csv > ../result/XE/section-data.csv
echo "XE done!"
java -Xmx16G cdac/in/gate/result/ResultProcessing -k ../papers/XL/key.csv -r ../papers/XL/response.csv -sa -av -ap ../papers/XL/applicant.csv > ../result/XL/section-data.csv
echo "XL done!"
