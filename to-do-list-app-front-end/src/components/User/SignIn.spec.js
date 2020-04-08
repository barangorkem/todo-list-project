
import { mount,createLocalVue } from '@vue/test-utils';
import VueRouter from 'vue-router';
import Vuetify from 'vuetify';
import SignIn from '../User/SignIn.vue'




describe('SignIn.vue', () => {
    test('setup correctly', () => {
        expect(true).toBe(true);
    })
  })

  describe('SignIn.vue', () => {
    test('clicking button increments the counter value by 1', () => {
        const wrapper = mount(SignIn)
        expect(wrapper.find('h3').text()).toBe('SignIn Page')
    })
  })


describe('SignIn.vue', () => {
    let wrapper;
    const routes = [
        {path: '/signin', name: 'signup'},
    ];
    const router = new VueRouter({routes});
    const localVue = createLocalVue();
    localVue.use(VueRouter);
    localVue.use(Vuetify);
    wrapper = mount(SignIn, {localVue, router});

    test('it should test that the submit button is enabled once the input fields has been filled', async () => {
        const button = wrapper.find('button');
        wrapper.setData({
           userData:{
            email: 'test@gmail.com',
            password: 'password',
           }
        });
        // This is the line that create the error
        wrapper.vm.$refs.form.validate();
        await wrapper.vm.$nextTick();
        expect(button.element.getAttribute('disabled')).toBe(null);
    })
});
