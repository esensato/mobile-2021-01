import 'package:flutter/material.dart';
import 'package:ven_carro/app/Splash.dart';

void main() {
  runApp(App());
}

class App extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Splash()
    );

  }

}