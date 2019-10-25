//da java 9 in poi per modularita`

module shared {
	requires java.rmi;
	exports implementations;
	exports interfaces;
}