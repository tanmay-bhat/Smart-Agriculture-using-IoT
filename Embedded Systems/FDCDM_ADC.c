//-----------------------------------------------------------------------------------------
//	FDCDM_ADC.c
//-----------------------------------------------------------------------------------------

// AUTH:	MADHUSUDHAN A S
// DATE:	30/07/2010

// MODIFIED BY:
// MODIFIED DATE:

// Program to interface ADC0809

// Target: PROJECT BOARD of PHILIPS P89V51RD2 ( PES-01-0809 )

// Tool chain: KEIL IDE

//--------------------------------------------------------------------------------------------------------------------
// Includes
//--------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------
// 16-bit SFR Definitions for "P89V51RD2"
//--------------------------------------------------------------------------------------------------------------------

sbit ADC09_MUX_A = P3^5;
sbit ADC09_MUX_B = P3^4;
// sbit ADC09_MUX_C = P2^6;

sbit ADC09_START = P3^3;


// sbit ADC09_EOC = P3^5;			// Input to Microcontroller acts as an interrupt, we need to poll this pin.
// sbit ADC09_OP_EN = P3^6;
// sbit ADC09_ALE = P3^7;

//--------------------------------------------------------------------------------------------------------------------
// Global VARIABLES
//--------------------------------------------------------------------------------------------------------------------

unsigned int ADC_Final=0;
unsigned char ADC_Data_Arr[10];
 int Voltage,Voltage1;
unsigned int Volt_Temp;


 data unsigned char Volt_Value[7]="00.00V";

//--------------------------------------------------------------------------------------------------------------------
// Global CONSTANTS
//--------------------------------------------------------------------------------------------------------------------

unsigned int ADC09_Start( bit Mux_Addr_B, bit Mux_Addr_A );

//--------------------------------------------------------------------------------------------------------------------
// Function PROTOTYPES
//--------------------------------------------------------------------------------------------------------------------


unsigned int ADC09_Start( bit Mux_Addr_B, bit Mux_Addr_A )
{
	unsigned char i=0;
	Voltage=0;

// Selection of ADC09 input channels based on arguments
	ADC09_MUX_A = Mux_Addr_A;					
	ADC09_MUX_B = Mux_Addr_B;
//	ADC09_MUX_C = Mux_Addr_C;

	ADC_Final = 0;
	ADC_Data_Arr[2] = 0;
// Increase the loop cycle for more accuracy.
	for( i=0; i<2; i++ )
	{

		ADC09_START = 1;
		MSDelay(1);									// 1 msec delay
		ADC09_START = 0;
		MSDelay( 10 );								// 1 msec delay
	
		ADC_Data_Arr[i] = P0;						// Store in an array
// P3 = ADC_Data_Arr[i];								// Testing
		ADC_Final = ADC_Final + ADC_Data_Arr[i];
	}

	ADC_Final = ADC_Final / 2;						// Do average based the loop cycles

// Following code is for Temperature Sensor LM35
	Voltage =(unsigned int)((( ADC_Final * 100.0 ) / 255.0 )*5.0);


	return Voltage;
}


/*
unsigned int ADC09_Start( bit Mux_Addr_C, bit Mux_Addr_B, bit Mux_Addr_A )
{
	unsigned char i=0;

// Selection of ADC09 input channels based on arguments
	ADC09_MUX_A = Mux_Addr_A;					
	ADC09_MUX_B = Mux_Addr_B;
	ADC09_MUX_C = Mux_Addr_C;

	ADC_Final = 0;
	ADC_Data_Arr[0] = 0;
// Increase the loop cycle for more accuracy.
	for( i=0; i<10; i++ )
	{
		ADC09_START = 0;
		ADC09_OP_EN = 0;
		ADC09_ALE = 0;
		
		ADC09_ALE = 1;
//		MSDelay(1);									// 1 msec delay
		ADC09_ALE = 0;
	
		ADC09_START = 1;
//		MSDelay(1);									// 1 msec delay
		ADC09_START = 0;
	
		while( ADC09_EOC == 0 )						// Wait till conversion complete
		{
			;
		}
		ADC09_OP_EN = 1;
		ADC_Data_Arr[i] = P0;						// Store in an array
// P3 = ADC_Data_Arr[i];								// Testing
		ADC09_OP_EN = 0;
		ADC_Final = ADC_Final + ADC_Data_Arr[i];
	}

	ADC_Final = ADC_Final / 10;						// Do average based the loop cycles

// Following code is for Temperature Sensor LM35
	Voltage =(unsigned int)((( ADC_Final * 5.0 ) / 255.0 ) * 100.0);
// P3 = Voltage;								// Testing

	Volt_Temp = Voltage - 9;

	Volt_Value[4] = (Volt_Temp%10) + 48;
	Volt_Temp = Volt_Temp / 10; 
	Volt_Value[3] = (Volt_Temp%10) + 48;
	Volt_Temp = Volt_Temp / 10; 
	Volt_Value[1] = (Volt_Temp%10) + 48;
	Volt_Temp = Volt_Temp / 10; 
	Volt_Value[0] = (Volt_Temp%10) + 48;

	return Voltage;
}
*/







