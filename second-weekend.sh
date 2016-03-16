javac cdac/in/gate/result/ResultProcessing.java

java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/AE/key.csv -r ../papers/AE/response.csv -an -av -ap ../papers/AE/applicant.csv > ../result/AE/result.txt
echo "AE done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/AG/key.csv -r ../papers/AG/response.csv -an -av -ap ../papers/AG/applicant.csv > ../result/AG/result.txt
echo "AG done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/CS/key.csv -r ../papers/CS/response.csv -an -av -ap ../papers/CS/applicant.csv > ../result/CS/result.txt
echo "CS done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/CE/key.csv -r ../papers/CE/response.csv -an -av -ap ../papers/CE/applicant.csv > ../result/CE/result.txt
echo "CE done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/EE/key.csv -r ../papers/EE/response.csv -an -av -ap ../papers/EE/applicant.csv > ../result/EE/result.txt
echo "EE done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/EY/key.csv -r ../papers/EY/response.csv -an -av -ap ../papers/EY/applicant.csv > ../result/EY/result.txt
echo "EY done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/MT/key.csv -r ../papers/MT/response.csv -an -av -ap ../papers/MT/applicant.csv > ../result/MT/result.txt
echo "MT done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/PI/key.csv -r ../papers/PI/response.csv -an -av -ap ../papers/PI/applicant.csv > ../result/PI/result.txt
echo "PI done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/TF/key.csv -r ../papers/TF/response.csv -an -av -ap ../papers/TF/applicant.csv > ../result/TF/result.txt
echo "TF done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/XE/key.csv -r ../papers/XE/response.csv -an -av -ap ../papers/XE/applicant.csv > ../result/XE/result.txt
echo "XE done!"
java -Xmx8G cdac/in/gate/result/ResultProcessing -k ../papers/XL/key.csv -r ../papers/XL/response.csv -an -av -ap ../papers/XL/applicant.csv > ../result/XL/result.txt
echo "XL done!"
