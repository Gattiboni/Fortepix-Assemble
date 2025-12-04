
import 'package:flutter/material.dart';

void main() => runApp(FortePixAdmin());

class FortePixAdmin extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('FortePix Admin - Flutter Web')),
        body: Center(child: Text('Painel Admin Flutter Web pronto para integração')),
      ),
    );
  }
}
