 //--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------

// AUTH: i-quad technologies
// DATE:	21/01/2013


// Tool chain: KEIL IDE

//--------------------------------------------------------------------------------------------------------------------
// Includes
//--------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------
// 16-bit SFR Definitions for "P89V51RD2"
//--------------------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------------------
// Global VARIABLES
//--------------------------------------------------------------------------------------------------------------------

xdata unsigned char Rx_Mob_Num[11]="0000000000";
unsigned char PWM_flag;

xdata unsigned char SMS_Send_Str[30] = "MOTOR = 0\nHUMIDITY=000%";
int Bulb_flag=0;
int  Fan_flag=0;
//--------------------------------------------------------------------------------------------------------------------
// Global CONSTANTS
//--------------------------------------------------------------------------------------------------------------------

code unsigned char GSM_Send_SMS_Comm[]="AT+CMGF=1\r\n";
code unsigned char GSM_Send_SMS_Comm1[]="AT+CMGS=\"8105807707\"\r\n";
code unsigned char GSM_Rx_SMS_Comm[]="AT+CNMI=2,2,0,0,0";

code char GSM_SMS1[]="SYSTEM STARTS";
code char Mb_Num[]="8073884516";

//--------------------------------------------------------
// Function PROTOTYPES
//--------------------------------------------------------------------------------------------------------------------

void GSM_Init( void );
void GSM_Send_SMS( char *Mb_Num, char *SMS );
void UART_Tx_char( char Tx_char );

//void GSM_Rx_SMS( void );
void SMS_Rx_Poll( void );
void PWM(unsigned char);

//--------------------------------------------------------------------------------------------------------------------
// void GSM_Init( void )
//--------------------------------------------------------------------------------------------------------------------
// Function Name:	void GSM_Init( void )
// Arguments	:	No arguments
// Return Value	:	No return value
// Description	:	This function is used to test the GSM module. If we send a command AT then
// the GSM module will reply OK.

void GSM_Init( void )
{
	UART_ST( "AT\r\n");
	//UART_ST( comm);
	//UART_Tx_char( 'A');
//UART_Tx_char( 'T' );
	//UART_Tx_char( 0x0D );				// ASCII value of CARRIAGE RETURN
}


//--------------------------------------------------------------------------------------------------------------------
// void UART_Tx_char( char Tx_char )
//--------------------------------------------------------------------------------------------------------------------
// Function Name:	void UART_Tx_char( char Tx_char )
// Arguments	:	No arguments
// Return Value	:	No return value
// Description	:	This function is used to send a single char through UART. After sending 
// a char, if receiving is enabled, then the received 1 byte data will be in Rx_data. If Tx_char
// and Rx_char, both are equal, then it comes out of while loop.

void UART_Tx_char( char Tx_char )
{
	SBUF = Tx_char;	
		
	// UART transmission starts
	Rx_data = 0x00;
	while(Tx_char ==Rx_data);

}


//--------------------------------------------------------------------------------------------------------------------
// void GSM_Send_SMS( char *Mb_Num, char *SMS )
//--------------------------------------------------------------------------------------------------------------------
// Function Name:	void GSM_Send_SMS( char *Mb_Num, char *SMS )
// Arguments	:	2 arguments
// *Mb_Num -> it receives a base address of a string which contains a mobile number.
// *SMS -> it receives a base address of a string which contains a text message.
// Return Value	:	No return value
// Description	:	This function is used to send an SMS. 

void GSM_Send_SMS( char *Mb_Num, char *SMS )
{
	//data unsigned char i;

// SMS send command	is sent through UART
//	for( i=0; GSM_Send_SMS_Comm[i] != '\0'; i++ )
	//	UART_Tx_char( GSM_Send_SMS_Comm[i] );
    UART_ST( GSM_Send_SMS_Comm);
	MSDelay(100);	
	UART_ST( GSM_Send_SMS_Comm1);
	//Rx_count = 0;						// Take UART Receiver array to zeroth location
	
	//P2 = 0x22;							// Testing
	MSDelay(100);						// Testing

//	UART_Tx_char( 0x22 );				// ASCII value of "
// Mobile number is sent through UART
//	while( *Mb_Num != '\0' )
	//	UART_Tx_char( *Mb_Num++ );

//	Rx_count = 0;						// Take UART Receiver array to zeroth location
	
	//P2 = 0x33;							// Testing
	MSDelay(100);						// Testing

	//UART_Tx_char( 0x22 );				// ASCII value of "
	//UART_Tx_char( 0x0D );				// ASCII value of CARRIAGE RETURN
//	UART_Tx_char( 0x0A );				// ASCII value of LINE FEED

//	Rx_count = 0;						// Take UART Receiver array to zeroth location
 UART_ST(SMS);
// Text message is sent through UART
	//while( *SMS != '\0' )
	//	UART_Tx_char( *SMS++ );
SBUF=0x1A;

	//Rx_count = 0;						// Take UART Receiver array to zeroth location
					// UART transmission starts
	
	//P2 = 0x1A;							// Testing
	//MSDelay(100);						// Testing

}


//void GSM_Rx_SMS( void )
//{
//	data unsigned char i;

 //SMS send command	is sent through UART
	//Rx_count = 0;						// Take UART Receiver array to zeroth location
	//for( i=0; GSM_Rx_SMS_Comm[i] != '\0'; i++ )
	//	UART_Tx_char( GSM_Rx_SMS_Comm[i] );

	//Rx_ALCD( );
//	Rx_count = 0;						// Take UART Receiver array to zeroth location
//	UART_Tx_char( 0x0D );				// ASCII value of CARRIAGE RETURN
//	Rx_ALCD( );
//}
/*
void SMS_Rx_Poll( void )
{
	data unsigned char i, j; 

// At this stage, we assume that the SMS as been received.
// The received SMS contains lots of extra informations, which are not needed. So check for -->  22"	

ALCD_Message( 0x80, "1" );			// Testing
// To retrive the mobile number of received SMS
	i = 0;
	while( Rx_data_arr[i] != '\0' )
	{
		if( (Rx_data_arr[i] == '+') && (Rx_data_arr[i+1] == '9') && (Rx_data_arr[i+2] == '1'))
			break;
	
		i++;
	}
// To store the mobile number of received SMS
	i = i+3;
	for( j=0; j<10; j++)
		Rx_Mob_Num[j] = Rx_data_arr[i];


	i = 0;
	while( Rx_data_arr[i] != '\0' )
	{
		if( (Rx_data_arr[i] == '2') && (Rx_data_arr[i+1] == '2') && (Rx_data_arr[i+2] == '"'))
			break;
		
		i++;
	}

// Here we are storing the SMS received at 5th location of array of structure.
// The received SMS format is -- Message(Max 30), 3 space

	i = i+5;
	j = i;
	while( i<(j+5) )
	{
// Scan for 3 space to come out of this while loop
		if (Rx_data_arr[i]=='L') 
		{
			if(Rx_data_arr[i+1]=='1')
			{ 
				ALCD_Message( 0x80, "REQUEST ACCEPTED" );
				ALCD_Message( 0xC0, "  BULB IS ON    " );
				Bulb_flag=1;
			}
			else if(Rx_data_arr[5]=='0')
			{ 
				ALCD_Message( 0x80, "REQUEST ACCEPTED" );
				ALCD_Message( 0xC0, "  BULB IS OFF   " );
				Bulb_flag=0;
			}
		}
		else if (Rx_data_arr[i]=='F') 
		{
			if(Rx_data_arr[7]=='1')
			{ 
				ALCD_Message( 0x80, "REQUEST ACCEPTED" );
				ALCD_Message( 0xC0, "  FAN IS ON     " );
				Fan_flag=1;
			}
			else if(Rx_data_arr[9]=='0')
			{ 
				ALCD_Message( 0x80, "REQUEST ACCEPTED" );
				ALCD_Message( 0xC0, "  FAN IS OFF    " );
				Fan_flag=0;
			}
		
		}
		break;
		
	}
}*/
