/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;


import client.scenes.EndScreenCtrl;
import client.scenes.HomeScreenCtrl;
import client.scenes.ImportActivityCtrl;
import client.scenes.MainCtrl;
import client.scenes.QuizScreenCtrl;
import client.scenes.HowToPlayCtrl;
import client.scenes.AdministrativeInterfaceCtrl;
import client.scenes.AddActivityCtrl;
import client.scenes.EditActivityCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;


public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(HomeScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuizScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EndScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(HowToPlayCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdministrativeInterfaceCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ImportActivityCtrl.class).in(Scopes.SINGLETON);
    }
}