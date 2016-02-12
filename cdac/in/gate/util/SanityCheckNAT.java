package cdac.in.gate.util;

public class SanityCheckNAT
{
	public static void main(String[] args)
	{
		System.out.println(args[0] + " : '" + sanitizeNATResponse(args[0]) + "'");
	}

	static String sanitizeNATResponse(String response)
	{
		response = response.trim();

		if(response.contains("http"))
		{
			response = response.replaceAll("http.*", "");
		}
		if(response.endsWith(".") && response.length() > 1)
		{
			response = response.substring(0, response.lastIndexOf("."));
		}
		if(response.endsWith("-") && response.length() > 1)
		{
			response = response.substring(0, response.lastIndexOf("-"));
		}
		if(".".equals(response))
		{
			response = "";
		}
		if("-".equals(response))
		{
			response = "";
		}
		
		return response;
	}

}

