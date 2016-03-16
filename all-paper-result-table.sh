javac cdac/in/gate/result/ResultProcessing.java

java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/AR/key.csv -r ../papers/AR/response.csv -rt -ap ../papers/AR/applicant.csv > ../result/AR/result-table.csv
echo "AR done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/BT/key.csv -r ../papers/BT/response.csv -rt -ap ../papers/BT/applicant.csv > ../result/BT/result-table.csv
echo "BT done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/CH/key.csv -r ../papers/CH/response.csv -rt -ap ../papers/CH/applicant.csv > ../result/CH/result-table.csv
echo "CH done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/CY/key.csv -r ../papers/CY/response.csv -rt -ap ../papers/CY/applicant.csv > ../result/CY/result-table.csv
echo "CY done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/EC/key.csv -r ../papers/EC/response.csv -rt -ap ../papers/EC/applicant.csv > ../result/EC/result-table.csv
echo "EC done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/GG/key.csv -r ../papers/GG/response.csv -rt -ap ../papers/GG/applicant.csv > ../result/GG/result-table.csv
echo "GG done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/IN/key.csv -r ../papers/IN/response.csv -rt -ap ../papers/IN/applicant.csv > ../result/IN/result-table.csv
echo "IN done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/MA/key.csv -r ../papers/MA/response.csv -rt -ap ../papers/MA/applicant.csv > ../result/MA/result-table.csv
echo "MA done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/ME/key.csv -r ../papers/ME/response.csv -rt -ap ../papers/ME/applicant.csv > ../result/ME/result-table.csv
echo "ME done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/MN/key.csv -r ../papers/MN/response.csv -rt -ap ../papers/MN/applicant.csv > ../result/MN/result-table.csv
echo "MN done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/PE/key.csv -r ../papers/PE/response.csv -rt -ap ../papers/PE/applicant.csv > ../result/PE/result-table.csv
echo "PE done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/PH/key.csv -r ../papers/PH/response.csv -rt -ap ../papers/PH/applicant.csv > ../result/PH/result-table.csv
echo "PH done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/AE/key.csv -r ../papers/AE/response.csv -rt -ap ../papers/AE/applicant.csv > ../result/AE/result-table.csv
echo "AE done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/AG/key.csv -r ../papers/AG/response.csv -rt -ap ../papers/AG/applicant.csv > ../result/AG/result-table.csv
echo "AG done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/CS/key.csv -r ../papers/CS/response.csv -rt -ap ../papers/CS/applicant.csv > ../result/CS/result-table.csv
echo "CS done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/CE/key.csv -r ../papers/CE/response.csv -rt -ap ../papers/CE/applicant.csv > ../result/CE/result-table.csv
echo "CE done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/EE/key.csv -r ../papers/EE/response.csv -rt -ap ../papers/EE/applicant.csv > ../result/EE/result-table.csv
echo "EE done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/EY/key.csv -r ../papers/EY/response.csv -rt -ap ../papers/EY/applicant.csv > ../result/EY/result-table.csv
echo "EY done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/MT/key.csv -r ../papers/MT/response.csv -rt -ap ../papers/MT/applicant.csv > ../result/MT/result-table.csv
echo "MT done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/PI/key.csv -r ../papers/PI/response.csv -rt -ap ../papers/PI/applicant.csv > ../result/PI/result-table.csv
echo "PI done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/TF/key.csv -r ../papers/TF/response.csv -rt -ap ../papers/TF/applicant.csv > ../result/TF/result-table.csv
echo "TF done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/XE/key.csv -r ../papers/XE/response.csv -rt -ap ../papers/XE/applicant.csv > ../result/XE/result-table.csv
echo "XE done!"
java -Xmx6G cdac/in/gate/result/ResultProcessing -k ../papers/XL/key.csv -r ../papers/XL/response.csv -rt -ap ../papers/XL/applicant.csv > ../result/XL/result-table.csv
echo "XL done!"

cat ../result/*/result-table.csv | grep -v 'RegistrationId,' > ../result/all-result-table.csv
