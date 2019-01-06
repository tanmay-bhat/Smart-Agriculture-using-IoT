///--------------------------------------------------------------------------------------------------------------------
//	ATVM_UART.c
//--------------------------------------------------------------------------------------------------------------------

// AUTH:	i-quad technologies
// DATE:	15/07/2012


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

unsigned char Rx_data;
idata unsigned char Rx_data_arr[100];
unsigned char Rx_count=0;			// Take UART Receiver array to zeroth location

bit Rx_ST_Flag = 0;						// Indicates UART receive of first byte 

//--------------------------------------------------------------------------------------------------------------------
// Global CONSTANTS
//--------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------
// Function PROTOTYPES
//--------------------------------------------------------------------------------------------------------------------

void Timer1M2_Init( unsigned char count );
void UART0M1_Tx_Init( void );
void UART_ST( unsigned char *Print_Str );
void MSDelay( unsigned int Milli_Sec );

void UART_ST( unsigned char *Print_Str )
{
	unsigned char i;


	for( i=0; *Print_Str!='\0'; i++ )
	{
		SBUF = *Print_Str;
		Print_Str++;
		MSDelay(1);								// 2 sec delay
	}

//SBUF = 0x0A;
	MSDelay(10);								// 2 sec delay

//SBUF = 0x0D;
	MSDelay(10);								// 2 sec delay
}	


void Timer1M2_Init( unsigned char count )
{

	TH1 = count;				// Reload value for Timer
	TMOD = 0x20;				// Mode 2 selection

}

//--------------------------------------------------------------------------------------------------------------------
// void UART0M1_Tx_Init( void )
//--------------------------------------------------------------------------------------------------------------------
// Function Name:	void UART0M1_Tx_Init( void )
// Arguments	:	No arguments
// Return Value	:	No return value
// Description	:	This function will initialize UART 0

void UART0M1_Tx_Init( void )
{
//	PCON = 0x00;
	SCON = 0x40;				// Mode 1 selection
	REN = 1;					// Enable Reception
	ES=1;
}
void UART0M1_ISR( void ) interrupt 4
{

	if( TI == 1 )						// For Transmit flag
	{
		TI = 0;

	}

	else if( RI == 1 )					// For Receive flag
	{
		RI = 0;
		Rx_ST_Flag = 1;
		Rx_data = SBUF;
		Rx_data_arr[Rx_count++] = Rx_data;
		Rx_data_arr[Rx_count] = '\0';
	}
}





