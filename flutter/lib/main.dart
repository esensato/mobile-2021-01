import 'package:flutter/material.dart';
import 'app/Lista.dart';
import 'app/Dado.dart';

void main() {
  runApp(Main());
}

class Main extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        body: Dado()
      )
    );

  }


}