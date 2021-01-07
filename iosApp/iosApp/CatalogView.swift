//
//  CatalogView.swift
//  iosApp
//
//  Created by Lauro Castaneda on 03/01/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import domain

struct CatalogListView: View{
    @ObservedObject var catalogViewModel = CatalogViewModel()
    var body: some View{
        ZStack{
            List(catalogViewModel.items, id:\.id){ item in
                CatalogListItemView(catalogItem: item)
            }
            VStack{
                Spacer()
                HStack{
                    Spacer()
                    ItemFormAlert(viewModel:catalogViewModel)
                }
            }
        }
        
    }
}

struct CatalogListView_Previews: PreviewProvider {
    static var previews: some View {
        CatalogListView()
    }
}

struct CatalogListItemView: View {
    var catalogItem:CatalogItem
    
    var body: some View {
        HStack{
            Image("image001").resizable()
                .frame(width: 50, height: 50)
            Text(catalogItem.name)
            Text("\(catalogItem.price)")
            Spacer()
        }
    }
}

struct CatalogListItemView_Previews: PreviewProvider{
    static var previews: some View{
        Group{
            CatalogListItemView(catalogItem: CatalogItem(id: 0, name: "nombre", price: 2))
            CatalogListItemView(catalogItem: CatalogItem(id: 1, name: "nombre", price: 2))
        }
        .previewLayout(.fixed(width: 300, height: 70))

    }
}

class CatalogViewModel: ObservableObject{
    @Published var items = [CatalogItem]()
    private let database = ConfigIosKt.createDB()
    private var dataSource:ClipItemCatalogDataSourceImp?
    
    init() {
        dataSource = ClipItemCatalogDataSourceImp(dataBase: database!)
        FlowUtilsKt.toCFlow(flow: dataSource!.getItems())
            .watch{ listItems in
                print("updated view")
                print("\(listItems)")
                self.objectWillChange.send()
                self.items=(listItems as! NSMutableArray as! [CatalogItem])
                //self.list.append(contentsOf: listItems as! NSMutableArray as! [CatalogItem])
                print("list:\(self.items)")
            }
    }
    
    func add(catalogItem:CatalogItem){
        dataSource?.insert(item: catalogItem)
    }
}


struct ItemFormAlert: View{
    @ObservedObject var viewModel:CatalogViewModel

    @State private var showingAlert = false
    var body: some View{
        NavigationView{
            Button(action: {
                self.showingAlert = true
            }){
                Text("+")
                    .font(.system(.largeTitle))
                    .frame(width: 77, height: 70)
                    .foregroundColor(Color.white)
                    .padding(.bottom, 7)
            }.background(Color.blue)
            .cornerRadius(38.5)
            .padding()
            .shadow(color: Color.black.opacity(0.3), radius: 3, x: 3, y: 3)
            if(self.showingAlert){
                NavigationLink(
                    destination: CatalogItemForm(viewModel: self.viewModel),
                    label: {
                        /*@START_MENU_TOKEN@*/Text("Navigate")/*@END_MENU_TOKEN@*/
                    })
            }
        }
        //.alert(isPresented:$showingAlert){
            /*Alert(title: Text("Important"),message: nil,
                  dismissButton: .default(Text("Got it!"), action: {
                    viewModel.add(catalogItem:CatalogItem(id: 0, name: "name", price: 3.1))
                  }))*/
        //}
    }
}

struct CatalogItemForm: View{
    @ObservedObject var viewModel:CatalogViewModel

    @State var fieldName = ""
    @State var fieldPrice = ""

    var body: some View{
        VStack{
            Text("Name")
                .padding()
            TextField("name", text:$fieldName).textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Text("Price")
                .padding()
            TextField("Price", text:$fieldPrice).textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()
            Button(action:{
                viewModel.add(catalogItem: CatalogItem(id: 0, name: fieldName, price: Double(fieldPrice) ?? 0))
            }){
                Text("Aceptar")
            }
        }
    }
}

struct CatalogItemForm_Previews: PreviewProvider{
    static var previews: some View{
        var showingAlert = false
    }
}
