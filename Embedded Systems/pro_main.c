#include<P89V51RD2.H>
#include<pro_alcd.c>
#include<pro_uart.c>
#include<PGSM.c>
#include<113_adc.c>
sbit ir=P1^2;
sbit moisture=P1^1;
sbit ph=P1^0;

sbit relay=P1^6;


idata unsigned char Disp_Line2[17]="HUMI = 000 C";
idata unsigned char Disp_Line3[17]="TEMP = 000 C";
int i=0;
int flag=0;

//int Bulb_flag=0;
//int  Fan_flag=0;
//sbit Bulb_Relay=P1^6;
//sbit Fan_Relay=P1^7;
void Device_Init( void );

void main()
{
		
	Device_Init();
	
	GSM_Init( );
	MSDelay(500);	
	//Fan_Relay=0;
	//Bulb_Relay=0;
	relay=0;
	 UART_ST( "AT+CMGF=1\r\n");
	MSDelay(500);	
	 UART_ST( "AT+CNMI=2,2,0,0,0\r\n");
	//peltiyar=1;
	while(1)
	{
		
		if(ir==1)
	{
			 ALCD_Message( 0x80, "INTRUDER " );
		ALCD_Message( 0xC0, " DETECTED" );
			MSDelay(1000);
			ALCD_Message( 0x01,   "" );
			GSM_Send_SMS( Mb_Num, "INTRUDER DETECTED" );
			
			}
	else
{
ALCD_Message( 0x01,   "" );
		}
	if(ph==1)
		{ 
			
			ALCD_Message( 0x01, "");
			ALCD_Message( 0x80, "CONTAMINATED");
			ALCD_Message( 0xC0, "HIGH");
					GSM_Send_SMS( Mb_Num, "CONTAMINATED HIGH" );
					MSDelay(1000);

		
			ALCD_Message( 0x01, "");
		}
		else
		{
			ALCD_Message( 0x01,   "" );
		}
		if(moisture==1)
		{ 
			
			ALCD_Message( 0x01, "");
			ALCD_Message( 0x80, "LAND IS WET");
	//		GSM_Send_SMS( Mb_Num, "MOISTURE IS HIGH" );
			MSDelay(1000);
			flag=1;
			ALCD_Message( 0x01, "");
		}
		else
		{
			
						ALCD_Message( 0x01, "");
			ALCD_Message( 0x80, "LAND IS DRY");
			if(flag==1)
			{
			GSM_Send_SMS( Mb_Num, "LAND IS DRY" );
				flag=0;
			}
	MSDelay(1000);
		ALCD_Message( 0x01, "");
			
			
		}
	
		ADC09_Start( 0, 0 );
		Voltage=(Voltage-75);
		i = Voltage;								
		Disp_Line3[9] = (Voltage%10)+48;
		Voltage = Voltage / 10;
		Disp_Line3[8] = (Voltage%10)+48;
		Voltage = Voltage / 10;
		Disp_Line3[7] = (Voltage%10)+48;	    
		ALCD_Message( 0x01,"" );
	  ALCD_Message( 0x80,Disp_Line3 );
		MSDelay(1500);
		ALCD_Message( 0x01,"" );
		
		if(i>35)
		{
			ALCD_Message( 0x01, "");
			ALCD_Message( 0x80, "TEMP IS HIGH");
		
			GSM_Send_SMS( Mb_Num, "TEMP IS HIGH" );
					MSDelay(2000);
			ALCD_Message( 0x01, "");

		}
		
			Voltage1 =	ADC09_Start( 0, 1 );
			Voltage1=(Voltage1-65);
		i = Voltage1;								
		Disp_Line2[9] = (Voltage1%10)+48;
		Voltage1 = Voltage1 / 10;
		Disp_Line2[8] = (Voltage1%10)+48;
		Voltage1 = Voltage1 / 10;
		Disp_Line2[7] = (Voltage1%10)+48;
	    
		ALCD_Message( 0x01,"" );
	  ALCD_Message( 0xC0,Disp_Line2 );
				MSDelay(1500);
				ALCD_Message( 0x01,"" );
		
		if(i>45)
		{
			ALCD_Message( 0x01, "");
			ALCD_Message( 0x80, "HUMIDITY IS HIGH");
		
			GSM_Send_SMS( Mb_Num, "HUMIDITY IS HIGH" );
					MSDelay(2000);
			ALCD_Message( 0x01, "");

		}
	
	
			  if( Rx_ST_Flag == 1 )
		{
			//ALCD_Message( 0xC0, "  SMS RECEIVED  " );
			MSDelay(500);								// 0.5 sec delay
			Rx_ST_Flag = 0;
			//SMS_Rx_Poll( );			
			MSDelay(500);
			Rx_count =0;								// Take UART Receiver array to zeroth location
		i=0;
			while(Rx_data_arr[i] !='\0')
			{
				
				//ALCD_Message( 0x80, Rx_data_arr);
		   
			 
				 if(Rx_data_arr[i] =='C')
				{ 
					//ALCD_Message( 0x01,   "" );
					//ALCD_Message( 0xC0,"FAN ON");
					 //Fan_Relay=1;
					//MSDelay(1000);
					Rx_count =0;
				} 
				 if(Rx_data_arr[i] =='X')
				{ 
					ALCD_Message( 0x01,   "" );
					ALCD_Message( 0xC0,"MOTOR IS ON");
					 relay=1;
					MSDelay(1000);
					ALCD_Message( 0x01,   "" );
					Rx_count =0;
				} 
				if(Rx_data_arr[i] =='Z')
				{ 
					ALCD_Message( 0x01,   "" );
					ALCD_Message( 0xC0,"MOTOR IS OFF");
					 relay=0;
					MSDelay(1000);
					ALCD_Message( 0x01,   "" );
					Rx_count =0;
				} 
		
				i++;
			}
		}
	}
		
}

	


void Device_Init( void )
{
	xdata unsigned char UC_count = 0xFD;	// For 9600 Buad Rate

	EA = 0;
	

	
	

	ALCD_Init( );
	

	UART0M1_Tx_Init( );
	
		
	Timer1M2_Init( UC_count );
	
	EA = 1;	
	
	TR1 = 1;								
ALCD_Message( 0x80, "WELCOME" );


	MSDelay(1000);
ALCD_Message( 0x01, "" );	

										// 0.1 sec delay
	

}

pro_main.c
Displaying 113_adc.c.
